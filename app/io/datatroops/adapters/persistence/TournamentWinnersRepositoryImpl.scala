package io.datatroops.adapters.persistence

import io.datatroops.domain.errors.RepositoryError
import io.datatroops.domain.models.TournamentWinner
import io.datatroops.domain.repositories.TournamentWinnersRepository
import io.datatroops.utils.RepositoryErrorHandling.handleDbError
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import java.util.UUID
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TournamentWinnersRepositoryImpl @Inject()(dbConfig: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends TournamentWinnersRepository {

  private val db = dbConfig.get[JdbcProfile].db
  private val profile = dbConfig.get[JdbcProfile].profile

  import profile.api._

  override def addTournamentWinner(winner: TournamentWinner): Future[Either[RepositoryError, Int]] = {
    handleDbError(db.run(tournamentWinners += winner), "Error adding tournament winner")
  }

  override def getTournamentWinner(id: UUID): Future[Either[RepositoryError, Option[TournamentWinner]]] = {
    handleDbError(db.run(tournamentWinners.filter(_.id === id).result.headOption), "Error fetching tournament winner")
  }

  override def getTournamentWinners(tournamentId: UUID): Future[Either[RepositoryError, Seq[TournamentWinner]]] = {
    handleDbError(db.run(tournamentWinners.filter(_.tournamentId === tournamentId).result), "Error fetching tournament winners")
  }

  override def deleteTournamentWinner(id: UUID): Future[Either[RepositoryError, Int]] = {
    handleDbError(db.run(tournamentWinners.filter(_.id === id).delete), "Error deleting tournament winner")
  }
}
