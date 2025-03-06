// @GENERATOR:play-routes-compiler
// @SOURCE:conf/routes

package io.datatroops.swagger;

import router.RoutesPrefix;

public class routes {
  
  public static final io.datatroops.swagger.ReverseSwaggerSpecController SwaggerSpecController = new io.datatroops.swagger.ReverseSwaggerSpecController(RoutesPrefix.byNamePrefix());

  public static class javascript {
    
    public static final io.datatroops.swagger.javascript.ReverseSwaggerSpecController SwaggerSpecController = new io.datatroops.swagger.javascript.ReverseSwaggerSpecController(RoutesPrefix.byNamePrefix());
  }

}
