package io.datatroops.adapters.persistence

import io.datatroops.domain.errors.RepositoryError
import io.datatroops.domain.models.GameSession
import io.datatroops.domain.repositories.GameSessionRepository
import io.datatroops.utils.RepositoryErrorHandling.handleDbError
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import java.util.UUID
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class GameSessionRepositoryImpl @Inject()(dbConfig: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends GameSessionRepository {
  private val db = dbConfig.get[JdbcProfile].db
  private val profile = dbConfig.get[JdbcProfile].profile

  import profile.api._

  override def getGameSessionURL(tournamentId: UUID): Future[Either[RepositoryError, Option[GameSession]]] = {
    handleDbError(db.run(gameSessions.filter(_.tournamentId === tournamentId).result.headOption), "Error creating tournament")
  }

}
