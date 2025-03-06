package io.datatroops.domain.repositories

import com.google.inject.ImplementedBy
import io.datatroops.adapters.persistence.ExampleRepositoryImpl

import java.util.UUID
import scala.concurrent.Future

@ImplementedBy(classOf[ExampleRepositoryImpl])
trait ExampleRepository {

  def get(id: UUID): Future[Option[String]]

}
