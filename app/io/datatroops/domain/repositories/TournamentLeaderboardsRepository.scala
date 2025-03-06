package io.datatroops.domain.repositories

import com.google.inject.ImplementedBy
import io.datatroops.adapters.persistence.TournamentLeaderboardsRepositoryImpl
import io.datatroops.dao.TournamentLeaderboardsTable
import io.datatroops.domain.errors.RepositoryError
import io.datatroops.domain.models.TournamentLeaderboard
import slick.lifted.TableQuery

import java.util.UUID
import scala.concurrent.Future

@ImplementedBy(classOf[TournamentLeaderboardsRepositoryImpl])
trait TournamentLeaderboardsRepository {
  val tournamentLeaderboards = TableQuery[TournamentLeaderboardsTable]

  def addLeaderboardEntry(entry: TournamentLeaderboard): Future[Either[RepositoryError, Int]]

  def getLeaderboardEntries(tournamentId: UUID): Future[Either[RepositoryError, Seq[TournamentLeaderboard]]]

  def updateUserScore(tournamentId: UUID, userId: UUID, newScore: BigDecimal): Future[Either[RepositoryError, Int]]
}
