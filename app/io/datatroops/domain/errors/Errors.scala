package io.datatroops.domain.errors

import java.util.UUID

sealed trait Error {
  def message: String
}

sealed trait RecordError extends Error

sealed trait RepositoryError extends RecordError

sealed trait ValidationError extends RecordError

case object RecordNotFound$ extends RepositoryError {
  val message: String = "Record not found"
}

case object InvalidRecordId$ extends ValidationError {
  val message: String = "Invalid Record ID"
}

case class DatabaseError(message: String, cause: Option[Throwable] = None) extends RepositoryError

case class TournamentNotAllowedToPlayException(userId: UUID,
                                               tournamentId: UUID,
                                               groupId: Option[UUID]
                                              ) extends Error {
  val message = s"User: $userId is not allowed to play in this tournament: $tournamentId ${groupId.map(id => s"and group: $id").getOrElse("")}."
}

case class TournamentNotStartingException(tournamentId: UUID) extends Error {
  val message = s"Tournament with id: $tournamentId is not starting within 5 minutes"
}

case class TournamentNotFoundException(tournamentId: UUID) extends Error {
  val message = s"No enabled Tournament with id: $tournamentId was found"
}

case class ExceptionProducingSingleMessageToKafka(ex: String) extends Error {
  val message = ex
}

case class ExceptionProducingBatchMessageToKafka(ex: String) extends Error {
  val message = ex
}

case class TournamentParticipantStatusException(status: String) extends Error {
  val message = s"Tournament participant's status: $status already exists"
}

case class ParticipantNotFoundException(tournamentId: UUID, userId: UUID) extends Error {
  val message: String =
    s"Participant with userId: $userId in tournamentId: $tournamentId was not found."
}
