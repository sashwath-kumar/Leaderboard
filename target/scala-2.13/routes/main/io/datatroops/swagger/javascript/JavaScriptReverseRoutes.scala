// @GENERATOR:play-routes-compiler
// @SOURCE:conf/routes

import play.api.routing.JavaScriptReverseRoute


import _root_.controllers.Assets.Asset

// @LINE:13
package io.datatroops.swagger.javascript {

  // @LINE:13
  class ReverseSwaggerSpecController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:13
    def specs: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "io.datatroops.swagger.SwaggerSpecController.specs",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "swagger-json"})
        }
      """
    )
  
    // @LINE:15
    def ui: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "io.datatroops.swagger.SwaggerSpecController.ui",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "swagger"})
        }
      """
    )
  
  }


}
