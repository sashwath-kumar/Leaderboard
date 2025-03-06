package io.datatroops.dao

import io.datatroops.domain.models.TournamentWinner
import slick.jdbc.PostgresProfile.api._

import java.util.UUID

class TournamentWinnersTable(tag: slick.lifted.Tag)
  extends Table[TournamentWinner](tag, "tournament_winners") {

  def id = column[UUID]("id", O.PrimaryKey, O.AutoInc)

  def tournamentId = column[UUID]("tournament_id")

  def userId = column[UUID]("user_id")

  def prizeId = column[UUID]("prize_id")

  def * = (id, tournamentId, userId, prizeId) <> ((TournamentWinner.apply _).tupled, TournamentWinner.unapply)
}
