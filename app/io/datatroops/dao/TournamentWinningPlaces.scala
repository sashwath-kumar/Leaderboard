package io.datatroops.dao

import io.datatroops.domain.models.TournamentWinningPlace
import slick.jdbc.PostgresProfile.api._

import java.util.UUID

class TournamentWinningPlaces(tag: Tag) extends Table[TournamentWinningPlace](tag, "tournament_winning_places") {
  def tournamentId = column[UUID]("tournament_id")

  def place = column[Int]("place")

  def prizeId = column[Option[UUID]]("prize_id")

  def consolationPlace = column[Boolean]("consolation_place")

  def pk = primaryKey("pk_tournament_place", (tournamentId, place))

  def tournamentFk = foreignKey("fk_tournament", tournamentId, TableQuery[Tournaments])(_.id, onDelete = ForeignKeyAction.Cascade)

  def * = (tournamentId, place, prizeId, consolationPlace) <> (TournamentWinningPlace.tupled, TournamentWinningPlace.unapply)
}
