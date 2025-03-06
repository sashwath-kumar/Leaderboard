package io.datatroops.domain.models

import play.api.libs.json.{Json, OFormat}

import java.util.UUID

case class GameSession(tournamentId: UUID, gameSessionURL: String)

object GameSession {
  implicit val format: OFormat[GameSession] = Json.format[GameSession]
}
