package io.datatroops.domain.models

import play.api.libs.json.{Json, OFormat}
import io.datatroops.utils.JsonFormats._

import java.sql.Timestamp
import java.util.UUID

case class Tournament(id: UUID, name: String, description: String, enabled: Boolean, startTime: Timestamp, endTime: Timestamp, gameId: UUID, startingBalance: BigDecimal, winningPlaces: Seq[WinningPlace] = Seq.empty, status: String)

object Tournament {
  implicit val format: OFormat[Tournament] = Json.format[Tournament]
}
