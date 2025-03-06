package io.datatroops.domain.repositories

import com.google.inject.ImplementedBy
import io.datatroops.adapters.persistence.TournamentParticipantsRepositoryImpl
import io.datatroops.dao.TournamentParticipantsTable
import io.datatroops.domain.errors.RepositoryError
import io.datatroops.domain.models.TournamentParticipants
import slick.lifted.TableQuery

import java.util.UUID
import scala.concurrent.Future

@ImplementedBy(classOf[TournamentParticipantsRepositoryImpl])
trait TournamentParticipantsRepository {
  val tournamentParticipants = TableQuery[TournamentParticipantsTable]

  def addTournamentParticipants(participant: TournamentParticipants): Future[Either[RepositoryError, Int]]

  def updateTournamentParticipantsStatus(participant: TournamentParticipants): Future[Either[RepositoryError, Int]]

  def isTournamentParticipantsStatusSame(participant: TournamentParticipants): Future[Either[RepositoryError, Boolean]]

  def isParticipantPresent(tournamentId: UUID, userId: UUID): Future[Either[RepositoryError, Boolean]]

  def getTournamentParticipant(tournamentId: UUID, userId: UUID): Future[Either[RepositoryError, Option[TournamentParticipants]]]

  def getTournamentParticipants(tournamentId: UUID): Future[Either[RepositoryError, Seq[TournamentParticipants]]]

  def deleteTournamentParticipants(id: UUID): Future[Either[RepositoryError, Int]]
}
