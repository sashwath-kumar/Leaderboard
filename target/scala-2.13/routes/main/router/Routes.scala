// @GENERATOR:play-routes-compiler
// @SOURCE:conf/routes

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:7
  HomeController_0: io.datatroops.controllers.HomeController,
  // @LINE:11
  TournamentController_1: io.datatroops.controllers.TournamentController,
  // @LINE:13
  SwaggerSpecController_2: io.datatroops.swagger.SwaggerSpecController,
  val prefix: String
) extends GeneratedRouter {

  @javax.inject.Inject()
  def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:7
    HomeController_0: io.datatroops.controllers.HomeController,
    // @LINE:11
    TournamentController_1: io.datatroops.controllers.TournamentController,
    // @LINE:13
    SwaggerSpecController_2: io.datatroops.swagger.SwaggerSpecController
  ) = this(errorHandler, HomeController_0, TournamentController_1, SwaggerSpecController_2, "/")

  def withPrefix(addPrefix: String): Routes = {
    val prefix = play.api.routing.Router.concatPrefix(addPrefix, this.prefix)
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, HomeController_0, TournamentController_1, SwaggerSpecController_2, prefix)
  }

  private val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix, """io.datatroops.controllers.HomeController.index()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """data/""" + "$" + """id<[^/]+>""", """io.datatroops.controllers.HomeController.getIdData(id:String)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """getTournaments""", """io.datatroops.controllers.TournamentController.getTournaments"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """swagger-json""", """io.datatroops.swagger.SwaggerSpecController.specs"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """swagger""", """io.datatroops.swagger.SwaggerSpecController.ui"""),
    Nil
  ).foldLeft(Seq.empty[(String, String, String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String, String, String)]
    case l => s ++ l.asInstanceOf[List[(String, String, String)]]
  }}


  // @LINE:7
  private lazy val io_datatroops_controllers_HomeController_index0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private lazy val io_datatroops_controllers_HomeController_index0_invoker = createInvoker(
    HomeController_0.index(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "io.datatroops.controllers.HomeController",
      "index",
      Nil,
      "GET",
      this.prefix + """""",
      """ An example controller showing a sample home page""",
      Seq()
    )
  )

  // @LINE:9
  private lazy val io_datatroops_controllers_HomeController_getIdData1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("data/"), DynamicPart("id", """[^/]+""", encodeable=true)))
  )
  private lazy val io_datatroops_controllers_HomeController_getIdData1_invoker = createInvoker(
    HomeController_0.getIdData(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "io.datatroops.controllers.HomeController",
      "getIdData",
      Seq(classOf[String]),
      "GET",
      this.prefix + """data/""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:11
  private lazy val io_datatroops_controllers_TournamentController_getTournaments2_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("getTournaments")))
  )
  private lazy val io_datatroops_controllers_TournamentController_getTournaments2_invoker = createInvoker(
    TournamentController_1.getTournaments,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "io.datatroops.controllers.TournamentController",
      "getTournaments",
      Nil,
      "POST",
      this.prefix + """getTournaments""",
      """""",
      Seq()
    )
  )

  // @LINE:13
  private lazy val io_datatroops_swagger_SwaggerSpecController_specs3_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("swagger-json")))
  )
  private lazy val io_datatroops_swagger_SwaggerSpecController_specs3_invoker = createInvoker(
    SwaggerSpecController_2.specs,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "io.datatroops.swagger.SwaggerSpecController",
      "specs",
      Nil,
      "GET",
      this.prefix + """swagger-json""",
      """""",
      Seq()
    )
  )

  // @LINE:15
  private lazy val io_datatroops_swagger_SwaggerSpecController_ui4_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("swagger")))
  )
  private lazy val io_datatroops_swagger_SwaggerSpecController_ui4_invoker = createInvoker(
    SwaggerSpecController_2.ui,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "io.datatroops.swagger.SwaggerSpecController",
      "ui",
      Nil,
      "GET",
      this.prefix + """swagger""",
      """""",
      Seq()
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:7
    case io_datatroops_controllers_HomeController_index0_route(params@_) =>
      call { 
        io_datatroops_controllers_HomeController_index0_invoker.call(HomeController_0.index())
      }
  
    // @LINE:9
    case io_datatroops_controllers_HomeController_getIdData1_route(params@_) =>
      call(params.fromPath[String]("id", None)) { (id) =>
        io_datatroops_controllers_HomeController_getIdData1_invoker.call(HomeController_0.getIdData(id))
      }
  
    // @LINE:11
    case io_datatroops_controllers_TournamentController_getTournaments2_route(params@_) =>
      call { 
        io_datatroops_controllers_TournamentController_getTournaments2_invoker.call(TournamentController_1.getTournaments)
      }
  
    // @LINE:13
    case io_datatroops_swagger_SwaggerSpecController_specs3_route(params@_) =>
      call { 
        io_datatroops_swagger_SwaggerSpecController_specs3_invoker.call(SwaggerSpecController_2.specs)
      }
  
    // @LINE:15
    case io_datatroops_swagger_SwaggerSpecController_ui4_route(params@_) =>
      call { 
        io_datatroops_swagger_SwaggerSpecController_ui4_invoker.call(SwaggerSpecController_2.ui)
      }
  }
}
