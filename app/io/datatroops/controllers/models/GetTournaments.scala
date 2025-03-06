package io.datatroops.controllers.models

import play.api.libs.json.{Json, OFormat}

import java.util.UUID

  case class GetTournaments(status: String,
                            userId: UUID)

  object GetTournaments {
    implicit val format: OFormat[GetTournaments] = Json.format[GetTournaments]
  }