package io.datatroops.dao

import io.datatroops.domain.models.TournamentLeaderboard
import slick.jdbc.PostgresProfile.api._

import java.util.UUID

class TournamentLeaderboardsTable(tag: Tag)
  extends Table[TournamentLeaderboard](tag, "tournament_leaderboards") {

  def tournamentId = column[UUID]("tournament_id")

  def userId = column[UUID]("user_id")

  def score = column[BigDecimal]("score", O.Default(BigDecimal(0.00)))

  def remainingBalance = column[BigDecimal]("remaining_balance", O.Default(BigDecimal(0.00)))

  def rank = column[Int]("rank")

  def pk = primaryKey("pk_tournament_leaderboards", (tournamentId, userId))

  def * = (tournamentId, userId, score, remainingBalance, rank) <> ((TournamentLeaderboard.apply _).tupled, TournamentLeaderboard.unapply)
}
