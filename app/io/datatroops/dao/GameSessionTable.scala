package io.datatroops.dao

import io.datatroops.domain.models.GameSession
import slick.jdbc.PostgresProfile.api._

import java.util.UUID

class GameSessionTable(tag: Tag) extends Table[GameSession](tag, "game_session") {
  def tournamentId = column[UUID]("tournament_id")

  def gameSessionURL = column[String]("game_session_url")

  def * = (
    tournamentId,
    gameSessionURL,
  ) <> (GameSession.tupled, GameSession.unapply)
}
