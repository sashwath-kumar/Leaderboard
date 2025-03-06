package io.datatroops.domain.repositories

import com.google.inject.ImplementedBy
import io.datatroops.adapters.persistence.TournamentWinnersRepositoryImpl
import io.datatroops.dao.TournamentWinnersTable
import io.datatroops.domain.errors.RepositoryError
import io.datatroops.domain.models.TournamentWinner
import slick.jdbc.PostgresProfile.api._

import java.util.UUID
import scala.concurrent.Future

@ImplementedBy(classOf[TournamentWinnersRepositoryImpl])
trait TournamentWinnersRepository {
  val tournamentWinners = TableQuery[TournamentWinnersTable]

  def addTournamentWinner(winner: TournamentWinner): Future[Either[RepositoryError, Int]]

  def getTournamentWinner(id: UUID): Future[Either[RepositoryError, Option[TournamentWinner]]]

  def getTournamentWinners(tournamentId: UUID): Future[Either[RepositoryError, Seq[TournamentWinner]]]

  def deleteTournamentWinner(id: UUID): Future[Either[RepositoryError, Int]]
}
