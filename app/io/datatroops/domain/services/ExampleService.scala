package io.datatroops.domain.services

import java.util.UUID
import io.datatroops.domain.repositories.ExampleRepository

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class ExampleService @Inject()(exampleRepository: ExampleRepository)(implicit val ec: ExecutionContext) {
  def findById(id: UUID): Future[Either[Exception, String]] = {
    exampleRepository.get(id).map {
      case Some(data) => Right(data)
      case None => Left(new Exception("Id Not found"))
    }
  }

}
