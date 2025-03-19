package io.datatroops.swagger

import com.iheart.playSwagger.{PrefixDomainModelQualifier, SwaggerSpecGenerator}
import play.api.Configuration
import play.api.libs.json.{JsNumber, JsObject, JsString}
import play.api.mvc._

import java.io.{BufferedReader, InputStream, InputStreamReader}
import java.util.stream.Collectors
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try
import java.nio.file.{Files, Paths}
import play.api.libs.json.Json
import play.api.Logger

class SwaggerSpecController @Inject()(
                                       cc: ControllerComponents,
                                       configuration: Configuration
                                     )(implicit executionContext: ExecutionContext)
  extends AbstractController(cc) {

  implicit private val cl: ClassLoader = getClass.getClassLoader

  private lazy val swagger = Action.async { _ =>
    Future
      .fromTry(generateSwaggerJson())
//      .map(appendDefaultHostAndPort)
      .map { swaggerJson =>
        Try {
          val jsonString = Json.prettyPrint(swaggerJson)
          println("Swagger Json Created")
          val filePath = Paths.get("swagger.json")
          Files.write(filePath, jsonString.getBytes)
        }.recover {
          case e => 
            Logger(getClass).error(s"Failed to save swagger.json: ${e.getMessage}", e)
        }
        Ok(swaggerJson)
      }
  }

//  private def appendDefaultHostAndPort(swaggerJson: JsObject): JsObject = {
//    swaggerJson + ("host" -> JsString("localhost:9000"))
//  }

  private def generateSwaggerJson(): Try[JsObject] = Try {
    val exacompPackages: Seq[String] =
      Try(configuration.get[Seq[String]]("swagger.exacomp.packages")).getOrElse(Seq.empty)
    val connectorPackages: Seq[String] =
      Try(configuration.get[Seq[String]]("swagger.connector.packages")).getOrElse(Seq.empty)
    val packages: Seq[String] = exacompPackages ++ connectorPackages
    val prefixDomainModelQualifier = PrefixDomainModelQualifier(packages: _*)
    SwaggerSpecGenerator(modelQualifier = prefixDomainModelQualifier)
  }.flatMap(_.generate("routes"))


  def specs: Action[AnyContent] = swagger

  def ui: Action[AnyContent] = Action { _ =>
    Try {
      val inputStream: InputStream = this.getClass.getResourceAsStream("/swaggerUI.html")
      try {
        new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining("\n"))
      } finally {
        inputStream.close()
      }
    }.fold(
      e => InternalServerError(s"Error loading Swagger UI: ${e.getMessage}"),
      content => Ok(content).as(HTML)
    )
  }
}