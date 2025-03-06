package io.datatroops.adapters.persistence

import io.datatroops.domain.errors.{DatabaseError, RepositoryError}
import io.datatroops.domain.models.TournamentParticipants
import io.datatroops.domain.repositories.TournamentParticipantsRepository
import io.datatroops.utils.RepositoryErrorHandling.handleDbError
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import java.util.UUID
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TournamentParticipantsRepositoryImpl @Inject()(dbConfig: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends TournamentParticipantsRepository {

  private val db = dbConfig.get[JdbcProfile].db
  private val profile = dbConfig.get[JdbcProfile].profile

  import profile.api._

  override def addTournamentParticipants(participant: TournamentParticipants): Future[Either[RepositoryError, Int]] = {
    handleDbError(db.run(tournamentParticipants += participant), "Error adding tournament participant")
  }

  override def updateTournamentParticipantsStatus(participant: TournamentParticipants): Future[Either[RepositoryError, Int]] = {
    handleDbError(db.run(tournamentParticipants.filter(tp => tp.tournamentId === participant.tournamentId && tp.userId === participant.userId).update(participant)), "Error updating tournament participant")
  }

  override def isTournamentParticipantsStatusSame(participant: TournamentParticipants): Future[Either[RepositoryError, Boolean]] = {
    handleDbError(db.run(tournamentParticipants.filter(tp => tp.tournamentId === participant.tournamentId && tp.userId === participant.userId && tp.status === participant.status).exists.result), "Error getting tournament participants status")
  }

  def isParticipantPresent(tournamentId: UUID, userId: UUID): Future[Either[RepositoryError, Boolean]] = {
    handleDbError(db.run(tournamentParticipants.filter(tp => tp.tournamentId === tournamentId && tp.userId === userId).exists.result), "Error while checking a tournament participants presence")
  }

  override def getTournamentParticipant(tournamentId: UUID, userId: UUID): Future[Either[RepositoryError, Option[TournamentParticipants]]] = {
    handleDbError(db.run(tournamentParticipants.filter(tp => tp.tournamentId === tournamentId && tp.userId === userId).result.headOption), "Error fetching tournament participant")
  }

  override def getTournamentParticipants(tournamentId: UUID): Future[Either[RepositoryError, Seq[TournamentParticipants]]] = {
    handleDbError(db.run(tournamentParticipants.filter(_.tournamentId === tournamentId).result), "Error fetching tournament participants")
  }

  override def deleteTournamentParticipants(id: UUID): Future[Either[RepositoryError, Int]] = {
    handleDbError(db.run(tournamentParticipants.filter(_.id === id).delete), "Error deleting tournament participant")
  }
}

