package io.datatroops.adapters.persistence

import io.datatroops.domain.errors.{DatabaseError, RepositoryError}
import io.datatroops.domain.models.TournamentTable
import io.datatroops.domain.repositories.TournamentRepository
import io.datatroops.utils.RepositoryErrorHandling.handleDbError
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import java.sql.Timestamp
import java.time.Instant
import java.util.UUID
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

@Singleton
class TournamentRepositoryImpl @Inject()(dbConfig: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends TournamentRepository {

  private val db = dbConfig.get[JdbcProfile].db
  private val profile = dbConfig.get[JdbcProfile].profile

  import profile.api._

  override def createTournament(tournament: TournamentTable): Future[Either[RepositoryError, Int]] = {
    handleDbError(db.run(tournaments += tournament), "Error creating tournament")
  }

  override def getTournament(id: UUID): Future[Either[RepositoryError, Option[TournamentTable]]] = {
    handleDbError(db.run(tournaments.filter(_.id === id).result.headOption), "Error fetching tournament")
  }

  override def getTournaments(status: String): Future[Either[RepositoryError, Seq[TournamentTable]]] = {

    val now: Timestamp = Timestamp.from(Instant.now())

    Try {
      val query = status.toLowerCase match {
        case "upcoming" =>
          tournaments.filter(_.starttime > now).sortBy(_.starttime.asc).take(20)
        case "inprogress" =>
          tournaments.filter(t => t.starttime <= now && t.endtime > now).sortBy(_.starttime.asc).take(20)
        case "completed" =>
          tournaments.filter(_.endtime <= now).sortBy(_.endtime.desc).take(20)
        case _ =>
          throw new IllegalArgumentException(s"Invalid status: $status")
      }
      db.run(query.result)
    } match {
      case Success(dbAction) =>
        handleDbError(dbAction, s"Error fetching tournaments with status $status")
      case Failure(ex: IllegalArgumentException) =>
        Future.successful(Left(DatabaseError(ex.getMessage)))
      case Failure(ex) =>
        Future.successful(Left(DatabaseError(s"Unexpected error: ${ex.getMessage}")))
    }
  }

  override def updateTournament(id: UUID, updatedTournament: TournamentTable): Future[Either[RepositoryError, Int]] = {
    handleDbError(db.run(tournaments.filter(_.id === id).update(updatedTournament)), "Error updating tournament")
  }

  override def deleteTournament(id: UUID): Future[Either[RepositoryError, Int]] = {
    handleDbError(db.run(tournaments.filter(_.id === id).delete), "Error deleting tournament")
  }
}
