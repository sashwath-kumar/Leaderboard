package io.datatroops.controllers

import io.datatroops.controllers.models.GetJoinTournamentSessionURL
import io.datatroops.controllers.models.GetTournamentById
import io.datatroops.controllers.models.ExitTournamentRequest
import io.datatroops.controllers.models.GetTournaments
import io.datatroops.domain.errors.RecordNotFound$
import io.datatroops.domain.services.TournamentService
import play.api.libs.json._
import play.api.mvc._

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TournamentController @Inject()(
                                      cc: ControllerComponents,
                                      tournamentService: TournamentService
                                    )(implicit ec: ExecutionContext)
  extends AbstractController(cc) {

  /**
   *
   * @return
   */
  def getTournaments: Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[GetTournaments].fold(
      error => Future.successful(BadRequest(Json.obj("error" -> JsError.toJson(error)))),
      getTournamentsRequest =>
        tournamentService.getTournaments(getTournamentsRequest).map {
          case Right(Nil) =>
            NotFound(Json.obj("error" -> "No tournaments found for the specified criteria."))
          case Right(tournaments) =>
            Ok(Json.obj("data" -> Json.toJson(tournaments)))
          case Left(error) =>
            InternalServerError(Json.obj("error" -> error.message))
        }
    )
  }

  def getTournamentById: Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[GetTournamentById].fold(
      error => Future.successful(BadRequest(Json.obj("error" -> JsError.toJson(error)))),
      getTournamentsRequest =>
        tournamentService.getTournamentById(getTournamentsRequest).map {
          case Right(None) =>
            NotFound(Json.obj("error" -> s"${RecordNotFound$.message} could not find enabled tournament with id ${getTournamentsRequest.tournamentId}."))
          case Right(tournaments) =>
            Ok(Json.obj("data" -> Json.toJson(tournaments)))
          case Left(error) =>
            InternalServerError(Json.obj("error" -> error.message))
        }
    )
  }

  /**
   *
   * @return
   */
  def getJoinTournamentURL: Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[GetJoinTournamentSessionURL].fold(
      error => Future.successful(BadRequest(Json.obj("error" -> JsError.toJson(error)))),
      getJoinTournamentRequest =>
        tournamentService.getJoinTournamentURL(getJoinTournamentRequest).map {
          case Right(None) =>
            NotFound(Json.obj("error" -> s"Game session url was not found for Tournament id: ${getJoinTournamentRequest.tournamentId}"))
          case Right(gameSessionURL) =>
            Ok(Json.obj("data" -> Json.toJson(gameSessionURL)))
          case Left(error) =>
            InternalServerError(Json.obj("error" -> error.message))
        }
    )
  }

  /**
   *
   * @return
   */
  def exitTournament: Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[ExitTournamentRequest].fold(
      error => Future.successful(BadRequest(Json.obj("error" -> JsError.toJson(error)))),
      exitTournamentRequest =>
        tournamentService.exitTournament(exitTournamentRequest).map {
          case Right(_) =>
            Created
          case Left(error) =>
            InternalServerError(Json.obj("error" -> error.message))
        }
    )
  }
}
