package io.datatroops.controllers

import io.datatroops.domain.services.ExampleService
import play.api.libs.json.{JsString, Json}
import play.api.mvc._

import java.util.UUID
import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents,
                               exampleService: ExampleService) extends BaseController {


  def getIdData(id: String): Action[AnyContent] = Action.async {
    val uuidId = UUID.fromString(id)
    exampleService.findById(uuidId).map {
      case Left(error) => NotFound(Json.obj("message" -> JsString(error.getMessage)))
      case Right(data) => Ok(Json.toJson(data))
    }
  }

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(io.datatroops.views.html.index())
  }
}
