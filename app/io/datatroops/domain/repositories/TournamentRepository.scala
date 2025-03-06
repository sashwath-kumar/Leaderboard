package io.datatroops.domain.repositories

import com.google.inject.ImplementedBy
import io.datatroops.adapters.persistence.TournamentRepositoryImpl
import io.datatroops.dao.Tournaments
import io.datatroops.domain.errors.RepositoryError
import io.datatroops.domain.models.TournamentTable
import slick.jdbc.PostgresProfile.api._

import java.util.UUID
import scala.concurrent.Future

@ImplementedBy(classOf[TournamentRepositoryImpl])
trait TournamentRepository {
  val tournaments = TableQuery[Tournaments]

  def createTournament(tournament: TournamentTable): Future[Either[RepositoryError, Int]]

  def getTournament(id: UUID): Future[Either[RepositoryError, Option[TournamentTable]]]

  def getTournaments(status: String): Future[Either[RepositoryError, Seq[TournamentTable]]]

  def updateTournament(id: UUID, updatedTournament: TournamentTable): Future[Either[RepositoryError, Int]]

  def deleteTournament(id: UUID): Future[Either[RepositoryError, Int]]
}
