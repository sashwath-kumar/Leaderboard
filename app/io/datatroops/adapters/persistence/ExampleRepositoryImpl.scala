package io.datatroops.adapters.persistence

import io.datatroops.domain.repositories.ExampleRepository

import java.util.UUID
import scala.concurrent.Future


class ExampleRepositoryImpl extends ExampleRepository {

  private val data: Map[UUID, String] = Map(
    UUID.fromString("8489b443-4671-4e15-b2de-365319e35a5e") -> "data"
  )

  override def get(id: UUID): Future[Option[String]] = {
    Future.successful(data.get(id))
  }

}
