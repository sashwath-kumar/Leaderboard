package io.datatroops.domain.models

import play.api.libs.json.{Json, OFormat}

import java.util.UUID

case class WinningPlace(place: Int, prizeId: Option[UUID], consolationPlace: Boolean)

object WinningPlace {
  implicit val format: OFormat[WinningPlace] = Json.format[WinningPlace]
}
