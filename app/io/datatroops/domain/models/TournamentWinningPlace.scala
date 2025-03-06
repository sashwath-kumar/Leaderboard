package io.datatroops.domain.models

import play.api.libs.json.{Json, OFormat}

import java.util.UUID

case class TournamentWinningPlace(
                                   tournamentId: UUID,
                                   place: Int,
                                   prizeId: Option[UUID],
                                   consolationPlace: Boolean = false
                                 )

object TournamentWinningPlace {
  implicit val format: OFormat[TournamentWinningPlace] = Json.format[TournamentWinningPlace]
}
