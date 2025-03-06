package io.datatroops.controllers.models

import play.api.libs.json.{Json, OFormat}

import java.util.UUID

case class ExitTournamentRequest(tournamentId: UUID, userId: UUID, gameId: Option[UUID] = None)

object ExitTournamentRequest {
  implicit val format: OFormat[ExitTournamentRequest] = Json.format[ExitTournamentRequest]
}