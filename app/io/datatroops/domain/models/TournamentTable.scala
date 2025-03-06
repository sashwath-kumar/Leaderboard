package io.datatroops.domain.models

import play.api.libs.json.{Json, OFormat}
import io.datatroops.utils.JsonFormats._
import java.sql.Timestamp
import java.util.UUID

case class TournamentTable(
                            id: UUID = UUID.randomUUID(),
                            name: Option[String],
                            description: Option[String],
                            enabled: Boolean = true,
                            starttime: Timestamp,
                            endtime: Timestamp,
                            ownerId: UUID,
                            gameId: UUID,
                            startingBalance: BigDecimal = BigDecimal(0.00),
                            lastModified: Timestamp,
                            groupId: Option[UUID],
                            disableGroupNotifications: Boolean = false,
                            groupMessage: Option[String],
                            disableNongroupNotifications: Boolean = false,
                            nongroupMessage: String,
                            privateViewing: Boolean = false
                          )

object TournamentTable {
  implicit val format: OFormat[TournamentTable] = Json.format[TournamentTable]
}
