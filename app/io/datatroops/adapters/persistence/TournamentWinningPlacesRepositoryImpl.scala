package io.datatroops.adapters.persistence

import io.datatroops.domain.errors.RepositoryError
import io.datatroops.domain.models.{TournamentWinningPlace, WinningPlace}
import io.datatroops.domain.repositories.TournamentWinningPlacesRepository
import io.datatroops.utils.RepositoryErrorHandling.handleDbError
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import java.util.UUID
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TournamentWinningPlacesRepositoryImpl @Inject()(dbConfig: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends TournamentWinningPlacesRepository {

  private val db = dbConfig.get[JdbcProfile].db
  private val profile = dbConfig.get[JdbcProfile].profile

  import profile.api._

  override def createWinningPlace(place: TournamentWinningPlace): Future[Either[RepositoryError, Int]] = {
    handleDbError(db.run(winningPlaces += place), "Error creating winning place")
  }

  private def rowToWinningPlace(row: TournamentWinningPlace): WinningPlace = {
    WinningPlace(
      place = row.place,
      prizeId = row.prizeId,
      consolationPlace = row.consolationPlace
    )
  }

  override def getWinningPlaces(tournamentId: UUID): Future[Either[RepositoryError, Seq[WinningPlace]]] = {
    handleDbError(db.run(winningPlaces.filter(_.tournamentId === tournamentId).result).map(_.map(rowToWinningPlace)), "Error fetching winning places")
  }

  override def updateTournamentWinningPlace(
                                             tournamentId: UUID,
                                             updatedTournamentWinningPlace: TournamentWinningPlace,
                                             place: Int
                                           ): Future[Either[RepositoryError, Int]] = {
    handleDbError(
      db.run(winningPlaces.filter(p => p.tournamentId === tournamentId && p.place === place).update(updatedTournamentWinningPlace)),
      "Error updating winning place"
    )
  }

  override def deleteTournamentWinningPlace(tournamentId: UUID, place: Int): Future[Either[RepositoryError, Int]] = {
    handleDbError(
      db.run(winningPlaces.filter(p => p.tournamentId === tournamentId && p.place === place).delete),
      "Error deleting winning place"
    )
  }
}
