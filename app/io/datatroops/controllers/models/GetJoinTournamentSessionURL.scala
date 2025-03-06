package io.datatroops.controllers.models

import play.api.libs.json.{Json, OFormat}

import java.util.UUID

case class GetJoinTournamentSessionURL(tournamentId: UUID, userId: UUID, gameId: Option[UUID] = None)

object GetJoinTournamentSessionURL {
  implicit val format: OFormat[GetJoinTournamentSessionURL] = Json.format[GetJoinTournamentSessionURL]
}