package io.datatroops.adapters.persistence

import io.datatroops.domain.errors.{RecordNotFound$, RepositoryError}
import io.datatroops.domain.repositories.UserRepository
import io.datatroops.utils.RepositoryErrorHandling.handleDbError

import java.util.UUID
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserRepositoryImpl @Inject()(implicit ec: ExecutionContext)
  extends UserRepository {

  private val userGrp: Map[UUID, Seq[UUID]] = Map(
    UUID.fromString("8489b443-4671-4e15-b2de-365319e35a5e") -> Seq(UUID.fromString("0574f12a-8ea5-4634-b0dd-26ca619cee2f"), UUID.fromString("ec2db9b7-fd1a-4f1a-a338-f1b52d1a23f0"), UUID.fromString("0fabedbb-2b42-41c7-b016-0100663ac4fe")),
    UUID.fromString("5abaecb6-f086-4146-8065-5e99107b0859") -> Seq(UUID.fromString("0574f12a-8ea5-4634-b0dd-26ca619cee2f")),
    UUID.fromString("736cd04e-3f50-4071-9471-f7e32ffd82b3") -> Seq(UUID.fromString("0574f12a-8ea5-4634-b0dd-26ca619cee2f"), UUID.fromString("0fabedbb-2b42-41c7-b016-0100663ac4fe")),
  )

  override def getUserGroupIds(userId: UUID): Future[Either[RepositoryError, Seq[UUID]]] = {
    handleDbError(Future {
      userGrp.get(userId) match {
        case Some(uuids) => uuids
        case None => throw new Exception(RecordNotFound$.message)
      }
    }, "Error fetching user groupIds")
  }
}
