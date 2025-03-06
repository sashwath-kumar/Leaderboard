package io.datatroops.domain.models

import play.api.libs.json.{Json, OFormat}

import java.util.UUID

case class TournamentWinner(
                             id: UUID,
                             tournamentId: UUID,
                             userId: UUID,
                             prizeId: UUID,
                           )

object TournamentWinner {
  implicit val format: OFormat[TournamentWinner] = Json.format[TournamentWinner]
}
