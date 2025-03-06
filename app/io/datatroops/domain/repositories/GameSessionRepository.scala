package io.datatroops.domain.repositories

import com.google.inject.ImplementedBy
import io.datatroops.adapters.persistence.GameSessionRepositoryImpl
import io.datatroops.dao.GameSessionTable
import io.datatroops.domain.errors.RepositoryError
import io.datatroops.domain.models.GameSession
import slick.lifted.TableQuery

import java.util.UUID
import scala.concurrent.Future

@ImplementedBy(classOf[GameSessionRepositoryImpl])
trait GameSessionRepository {
  val gameSessions = TableQuery[GameSessionTable]

  def getGameSessionURL(tournamentId: UUID): Future[Either[RepositoryError, Option[GameSession]]]

}

