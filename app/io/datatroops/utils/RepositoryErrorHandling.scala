package io.datatroops.utils

import io.datatroops.domain.errors.{DatabaseError, RepositoryError}

import scala.concurrent.{ExecutionContext, Future}

object RepositoryErrorHandling {

  def handleDbError[T](operation: => Future[T], errorMessage: String)(implicit ec: ExecutionContext): Future[Either[RepositoryError, T]] = {
    operation.map(Right(_)).recover {
      case ex: Throwable => Left(DatabaseError(s"$errorMessage: ${ex.getMessage}", Some(ex)))
    }
  }

}
