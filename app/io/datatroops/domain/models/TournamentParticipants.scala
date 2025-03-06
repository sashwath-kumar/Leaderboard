package io.datatroops.domain.models

import play.api.libs.json.{Json, OFormat}

import java.util.UUID

case class TournamentParticipants(
                             id: UUID = UUID.randomUUID(),
                             tournamentId: UUID,
                             userId: UUID,
                             status: String
                           )

object TournamentParticipants {
  implicit val format: OFormat[TournamentParticipants] = Json.format[TournamentParticipants]
}
