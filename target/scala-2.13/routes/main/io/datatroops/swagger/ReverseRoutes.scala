// @GENERATOR:play-routes-compiler
// @SOURCE:conf/routes

import play.api.mvc.Call


import _root_.controllers.Assets.Asset

// @LINE:13
package io.datatroops.swagger {

  // @LINE:13
  class ReverseSwaggerSpecController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:13
    def specs: Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "swagger-json")
    }
  
    // @LINE:15
    def ui: Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "swagger")
    }
  
  }


}
