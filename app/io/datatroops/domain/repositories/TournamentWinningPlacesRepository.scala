package io.datatroops.domain.repositories

import com.google.inject.ImplementedBy
import io.datatroops.adapters.persistence.TournamentWinningPlacesRepositoryImpl
import io.datatroops.dao.TournamentWinningPlaces
import io.datatroops.domain.errors.RepositoryError
import io.datatroops.domain.models.{TournamentWinningPlace, WinningPlace}
import slick.jdbc.PostgresProfile.api._

import java.util.UUID
import scala.concurrent.Future

@ImplementedBy(classOf[TournamentWinningPlacesRepositoryImpl])
trait TournamentWinningPlacesRepository {
  val winningPlaces = TableQuery[TournamentWinningPlaces]

  def createWinningPlace(place: TournamentWinningPlace): Future[Either[RepositoryError, Int]]

  def getWinningPlaces(id: UUID): Future[Either[RepositoryError, Seq[WinningPlace]]]

  def deleteTournamentWinningPlace(id: UUID, place: Int): Future[Either[RepositoryError, Int]]

  def updateTournamentWinningPlace(id: UUID, updatedTournamentWinningPlace: TournamentWinningPlace, place: Int): Future[Either[RepositoryError, Int]]
}
