package io.datatroops.domain.models

import play.api.libs.json.Json

import java.util.UUID

case class TournamentPlayer(tournamentId: UUID, userId: UUID, gameId: Option[UUID], status: String)

object TournamentPlayer {
  implicit val format = Json.format[TournamentPlayer]
}
