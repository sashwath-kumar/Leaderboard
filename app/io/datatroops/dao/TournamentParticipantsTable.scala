package io.datatroops.dao

import io.datatroops.domain.models.TournamentParticipants
import slick.jdbc.PostgresProfile.api._

import java.util.UUID

class TournamentParticipantsTable(tag: slick.lifted.Tag)
  extends Table[TournamentParticipants](tag, "tournament_participants") {

  def id = column[UUID]("id")

  def tournamentId = column[UUID]("tournament_id")

  def userId = column[UUID]("user_id")

  def status = column[String]("status")

  def pk = primaryKey("pk_tournament_user", (tournamentId, userId))

  def * = (id, tournamentId, userId, status) <> ((TournamentParticipants.apply _).tupled, TournamentParticipants.unapply)

}
