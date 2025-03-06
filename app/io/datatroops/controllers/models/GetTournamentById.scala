package io.datatroops.controllers.models

import play.api.libs.json.{Json, OFormat}

import java.util.UUID

case class GetTournamentById(tournamentId: UUID,
                             userId: UUID)

object GetTournamentById {
  implicit val format: OFormat[GetTournamentById] = Json.format[GetTournamentById]
}