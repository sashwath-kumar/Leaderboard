package io.datatroops.domain.services

import akka.Done
import io.datatroops.controllers.models.{ExitTournamentRequest, GetJoinTournamentSessionURL, GetTournamentById, GetTournaments}
import io.datatroops.controllers.models.{ExitTournamentRequest, GetJoinTournamentSessionURL, GetTournamentById, GetTournaments}
import io.datatroops.domain.errors.{Error, ParticipantNotFoundException, RepositoryError, TournamentNotAllowedToPlayException, TournamentNotFoundException, TournamentNotStartingException, TournamentParticipantStatusException}
import io.datatroops.domain.models._
import io.datatroops.domain.repositories._

import java.sql.Timestamp
import java.time.{Duration, Instant}
import java.util.UUID
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class TournamentService @Inject()(tournamentRepository: TournamentRepository,
                                  tournamentWinnersRepository: TournamentWinnersRepository,
                                  tournamentWinningPlacesRepository: TournamentWinningPlacesRepository,
                                  tournamentLeaderboardsRepository: TournamentLeaderboardsRepository,
                                  gameSessionRepository: GameSessionRepository,
                                  userRepository: UserRepository,
                                  kafkaProducerService: KafkaProducerService,
                                  tournamentParticipantsRepository: TournamentParticipantsRepository)
                                 (implicit val ec: ExecutionContext) {

  private def getTournamentFromTournamentTable(tournamentTable: TournamentTable, statusOption: Option[String] = None): Tournament = {

    val status = statusOption match {
      case Some(status) => status
      case None => determineStatus(tournamentTable.starttime, tournamentTable.endtime)
    }

    Tournament(id = tournamentTable.id, name = tournamentTable.name.getOrElse(""), description = tournamentTable.description.getOrElse(""), enabled = tournamentTable.enabled, startTime = tournamentTable.starttime, endTime = tournamentTable.endtime, gameId = tournamentTable.gameId, startingBalance = tournamentTable.startingBalance, winningPlaces = Seq.empty, status = status)
  }

  private def determineStatus(startTime: Timestamp, endTime: Timestamp): String = {
    val now = Timestamp.from(Instant.now())
    if (now.before(startTime)) "Upcoming"
    else if (now.after(endTime)) "Completed"
    else "InProgress"
  }

  private def addWinningPlacesToTournament(
                                            tournaments: Either[RepositoryError, Seq[Tournament]]
                                          ): Future[Either[RepositoryError, Seq[Tournament]]] = {
    tournaments match {
      case Right(tournamentList) =>
        val updatedTournaments = Future.sequence(
          tournamentList.map { tournament =>
            tournamentWinningPlacesRepository.getWinningPlaces(tournament.id).map {
              case Right(winningPlaces) =>
                tournament.copy(winningPlaces = if (winningPlaces.nonEmpty) winningPlaces else Seq.empty)
              case Left(_) => tournament // Keep the original tournament if fetching winning places fails
            }
          }
        )
        updatedTournaments.map(Right(_))
      case Left(error) =>
        Future.successful(Left(error))
    }
  }

  private def filterTournamentsByGroup(
                                        groupIdsResult: Either[RepositoryError, Seq[UUID]],
                                        tournamentsResult: Either[RepositoryError, Seq[TournamentTable]]
                                      ): Either[RepositoryError, Seq[TournamentTable]] = {
    for {
      groupIds <- groupIdsResult
      filteredTournaments <- tournamentsResult.map { tournaments =>
        tournaments.filter { tournament =>
          (tournament.privateViewing, tournament.groupId) match {
            case (true, Some(groupId)) if groupIds.contains(groupId) => true
            case (true, Some(_)) => false
            case _ => true
          }
        }
      }
    } yield filteredTournaments
  }

  private def isUserAllowedToPlayTournament(
                                             userId: UUID,
                                             groupIdsResult: Either[RepositoryError, Seq[UUID]],
                                             tournamentResult: Either[RepositoryError, Option[TournamentTable]]
                                           ): Either[Error, Option[TournamentTable]] = {
    for {
      groupIds <- groupIdsResult
      tournamentDetails <- tournamentResult.flatMap {
        case Some(tournament) =>
          val isUserAllowedToPlay = (tournament.privateViewing, groupIds, tournament.groupId) match {
            case (true, groupIds, Some(groupId)) =>
              groupIds.contains(groupId)
            case (_, _, _) => true
          }
          if (isUserAllowedToPlay)
            Right(Some(tournament))
          else
            Left(TournamentNotAllowedToPlayException(userId, tournamentId = tournament.id, groupId = tournament.groupId))
        case None => Right(None)
      }
    } yield tournamentDetails
  }

  def getTournaments(request: GetTournaments): Future[Either[RepositoryError, Seq[Tournament]]] = {
    for {
      userGroupIdsResult <- userRepository.getUserGroupIds(request.userId)
      allTournamentsResult <- tournamentRepository.getTournaments(request.status)
      filteredTournaments = filterTournamentsByGroup(userGroupIdsResult, allTournamentsResult)
      tournamentsWithoutWinnings = filteredTournaments.map(_.map(getTournamentFromTournamentTable(_, Some(request.status))))
      tournaments <- addWinningPlacesToTournament(tournamentsWithoutWinnings)
    } yield tournaments
  }

  def getTournamentById(request: GetTournamentById): Future[Either[Error, Option[Tournament]]] = {
    for {
      userGroupIdsResult <- userRepository.getUserGroupIds(request.userId)
      getTournamentResult <- tournamentRepository.getTournament(request.tournamentId)
      allowedTournament = isUserAllowedToPlayTournament(request.userId, userGroupIdsResult, getTournamentResult)
      tournamentInfo = allowedTournament.flatMap {
        case Some(tournamentInfo) => Right(Some(getTournamentFromTournamentTable(tournamentInfo)))
        case None => Right(None)
      }
    } yield tournamentInfo
  }

  def isTournamentStartingOrInprogress(tournament: Either[Error, Option[Tournament]], tournamentId: UUID): Future[Either[Error, Boolean]] = {
    tournament match {
      case Right(Some(t)) =>
        if (t.status == "InProgress")
          Future.successful(Right(true))
        else {
          val timeUntilStart = Duration.between(Instant.now(), t.startTime.toInstant).toMinutes
          Future.successful(Right(timeUntilStart > 0 && timeUntilStart <= 5))
        }
      case Right(None) =>
        Future.successful(Left(TournamentNotFoundException(tournamentId)))
      case Left(error) =>
        Future.successful(Left(error))
    }
  }

  def getJoinTournamentURL(
                            getJoinTournamentRequest: GetJoinTournamentSessionURL
                          ): Future[Either[Error, Option[GameSession]]] = {

    val participant = TournamentParticipants(
      tournamentId = getJoinTournamentRequest.tournamentId,
      userId = getJoinTournamentRequest.userId,
      status = "joined"
    )
    val tournamentPlayer = TournamentPlayer(
      getJoinTournamentRequest.tournamentId,
      getJoinTournamentRequest.userId,
      getJoinTournamentRequest.gameId,
      "joined"
    )

    for {
      isParticipantPresent <- tournamentParticipantsRepository.isParticipantPresent(participant.tournamentId, participant.userId)

      participantCheckResult <- isParticipantPresent match {
        case Right(true) =>
          handleParticipantStatus(
            participant = participant,
            status = "joined",
            onKafkaEvent = tournamentPlayer
          )

        case Right(false) =>
          addParticipantAndSendEvent(
            participant,
            tournamentPlayer
          )

        case Left(error) =>
          Future.successful(Left(error))
      }

      result <- participantCheckResult match {
        case Right(_) =>
          for {
            getTournamentResult <- getTournamentById(
              GetTournamentById(getJoinTournamentRequest.tournamentId, getJoinTournamentRequest.userId)
            )

            isStartingResult <- isTournamentStartingOrInprogress(getTournamentResult, getJoinTournamentRequest.tournamentId)

            gameSessionResult <- isStartingResult match {
              case Right(true) =>
                gameSessionRepository.getGameSessionURL(getJoinTournamentRequest.tournamentId)

              case Right(false) =>
                Future.successful(Left(TournamentNotStartingException(getJoinTournamentRequest.tournamentId)))

              case Left(error) =>
                Future.successful(Left(error))
            }
          } yield gameSessionResult

        case Left(error) =>
          Future.successful(Left(error))
      }
    } yield result
  }

  private def addParticipantAndSendEvent(
                                          participant: TournamentParticipants,
                                          kafkaEvent: TournamentPlayer
                                        ): Future[Either[Error, Done]] = {
    for {
      _ <- tournamentParticipantsRepository.addTournamentParticipants(participant)

      _ <- kafkaProducerService.sendTournamentPlayer(kafkaEvent.tournamentId.toString, kafkaEvent)
    } yield Right(Done)
  }


  def exitTournament(exitTournamentRequest: ExitTournamentRequest): Future[Either[Error, Done]] = {
    val tournamentParticipant = TournamentParticipants(
      tournamentId = exitTournamentRequest.tournamentId,
      userId = exitTournamentRequest.userId,
      status = "exited"
    )

    for {
      isParticipantPresent <- tournamentParticipantsRepository
        .isParticipantPresent(tournamentParticipant.tournamentId, tournamentParticipant.userId)

      result <- isParticipantPresent match {
        case Right(false) =>
          Future.successful(Left(ParticipantNotFoundException(tournamentParticipant.tournamentId, tournamentParticipant.userId)))

        case Left(error) =>
          Future.successful(Left(error))

        case Right(true) =>
          handleParticipantStatus(tournamentParticipant, "exited", TournamentPlayer(
            exitTournamentRequest.tournamentId,
            exitTournamentRequest.userId,
            exitTournamentRequest.gameId,
            "exited"
          ))
      }
    } yield result
  }

  private def handleParticipantStatus(
                                       participant: TournamentParticipants,
                                       status: String,
                                       onKafkaEvent: TournamentPlayer
                                     ): Future[Either[Error, Done]] = {
    for {
      statusCheck <- tournamentParticipantsRepository.isTournamentParticipantsStatusSame(participant.copy(status = status))

      result <- statusCheck match {
        case Right(true) =>
          Future.successful(Left(TournamentParticipantStatusException(status)))

        case Right(false) =>
          updateStatusAndSendEvent(participant.copy(status = status), onKafkaEvent)

        case Left(error) =>
          Future.successful(Left(error))
      }
    } yield result
  }

  private def updateStatusAndSendEvent(
                                        participant: TournamentParticipants,
                                        kafkaEvent: TournamentPlayer
                                      ): Future[Either[Error, Done]] = {
    for {
      _ <- tournamentParticipantsRepository.updateTournamentParticipantsStatus(participant)

      _ <- kafkaProducerService.sendTournamentPlayer(kafkaEvent.tournamentId.toString, kafkaEvent)
    } yield Right(Done)
  }


}
