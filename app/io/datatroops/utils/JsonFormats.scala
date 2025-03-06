package io.datatroops.utils

import play.api.libs.json._

import java.sql.Timestamp

object JsonFormats {
  implicit val timestampFormat: Format[Timestamp] = new Format[Timestamp] {
    override def reads(json: JsValue): JsResult[Timestamp] = json.validate[String].map(Timestamp.valueOf)

    override def writes(ts: Timestamp): JsValue = JsString(ts.toString)
  }
}
