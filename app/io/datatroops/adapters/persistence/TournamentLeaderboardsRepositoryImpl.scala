package io.datatroops.adapters.persistence

import io.datatroops.domain.errors.RepositoryError
import io.datatroops.domain.models.TournamentLeaderboard
import io.datatroops.domain.repositories.TournamentLeaderboardsRepository
import io.datatroops.utils.RepositoryErrorHandling.handleDbError
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import java.util.UUID
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TournamentLeaderboardsRepositoryImpl @Inject()(dbConfig: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends TournamentLeaderboardsRepository {

  private val db = dbConfig.get[JdbcProfile].db
  private val profile = dbConfig.get[JdbcProfile].profile

  import profile.api._

  override def addLeaderboardEntry(entry: TournamentLeaderboard): Future[Either[RepositoryError, Int]] = {
    handleDbError(db.run(tournamentLeaderboards += entry), "Error adding leaderboard entry")
  }

  override def getLeaderboardEntries(tournamentId: UUID): Future[Either[RepositoryError, Seq[TournamentLeaderboard]]] = {
    handleDbError(db.run(tournamentLeaderboards.filter(_.tournamentId === tournamentId).result), "Error fetching leaderboard entries")
  }

  override def updateUserScore(
                                tournamentId: UUID,
                                userId: UUID,
                                newScore: BigDecimal
                              ): Future[Either[RepositoryError, Int]] = {
    handleDbError(
      db.run(
        tournamentLeaderboards
          .filter(e => e.tournamentId === tournamentId && e.userId === userId)
          .map(_.score)
          .update(newScore)
      ),
      "Error updating user score"
    )
  }
}
