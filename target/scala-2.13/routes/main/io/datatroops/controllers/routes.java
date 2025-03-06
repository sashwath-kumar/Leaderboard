// @GENERATOR:play-routes-compiler
// @SOURCE:conf/routes

package io.datatroops.controllers;

import router.RoutesPrefix;

public class routes {
  
  public static final io.datatroops.controllers.ReverseHomeController HomeController = new io.datatroops.controllers.ReverseHomeController(RoutesPrefix.byNamePrefix());
  public static final io.datatroops.controllers.ReverseTournamentController TournamentController = new io.datatroops.controllers.ReverseTournamentController(RoutesPrefix.byNamePrefix());

  public static class javascript {
    
    public static final io.datatroops.controllers.javascript.ReverseHomeController HomeController = new io.datatroops.controllers.javascript.ReverseHomeController(RoutesPrefix.byNamePrefix());
    public static final io.datatroops.controllers.javascript.ReverseTournamentController TournamentController = new io.datatroops.controllers.javascript.ReverseTournamentController(RoutesPrefix.byNamePrefix());
  }

}
