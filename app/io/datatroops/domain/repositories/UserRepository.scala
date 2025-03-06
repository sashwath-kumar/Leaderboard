package io.datatroops.domain.repositories

import com.google.inject.ImplementedBy
import io.datatroops.adapters.persistence.UserRepositoryImpl
import io.datatroops.domain.errors.RepositoryError

import java.util.UUID
import scala.concurrent.Future

@ImplementedBy(classOf[UserRepositoryImpl])
trait UserRepository {

  def getUserGroupIds(userId: UUID): Future[Either[RepositoryError, Seq[UUID]]]

}
