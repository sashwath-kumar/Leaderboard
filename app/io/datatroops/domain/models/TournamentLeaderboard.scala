package io.datatroops.domain.models

import play.api.libs.json.{Json, OFormat}

import java.util.UUID

case class TournamentLeaderboard(
                                  tournamentId: UUID,
                                  userId: UUID,
                                  score: BigDecimal = 0.00,
                                  remainingBalance: BigDecimal = 0.00,
                                  rank: Int
                                )

object TournamentLeaderboard {
  implicit val format: OFormat[TournamentLeaderboard] = Json.format[TournamentLeaderboard]
}
