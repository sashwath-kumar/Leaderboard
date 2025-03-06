package io.datatroops.modules

import com.google.inject.AbstractModule
import io.datatroops.adapters.persistence._
import io.datatroops.domain.repositories._

class RepositoryModule extends AbstractModule {
  override def configure(): Unit =
    bind(classOf[ExampleRepository]).to(classOf[ExampleRepositoryImpl])

  bind[ExampleRepository](classOf[ExampleRepository]).asEagerSingleton()
  bind(classOf[TournamentRepository]).to(classOf[TournamentRepositoryImpl])
  bind(classOf[TournamentWinningPlacesRepository]).to(classOf[TournamentWinningPlacesRepositoryImpl])
  bind(classOf[TournamentWinnersRepository]).to(classOf[TournamentWinnersRepositoryImpl])
  bind(classOf[TournamentLeaderboardsRepository]).to(classOf[TournamentLeaderboardsRepositoryImpl])
  bind(classOf[UserRepository]).to(classOf[UserRepositoryImpl])
}
