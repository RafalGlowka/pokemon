package com.glowka.rafal.pokemon.modules

import com.glowka.rafal.pokemon.data.mapper.AllPokemonToPokemonMapper
import com.glowka.rafal.pokemon.data.mapper.AllPokemonToPokemonMapperImpl
import com.glowka.rafal.pokemon.data.repository.FavouritesRepositoryImpl
import com.glowka.rafal.pokemon.data.repository.PokemonRepositoryImpl
import com.glowka.rafal.pokemon.domain.repository.FavouritesRepository
import com.glowka.rafal.pokemon.domain.repository.PokemonRepository
import com.glowka.rafal.pokemon.domain.usecase.*
import org.koin.dsl.module

/**
 * Created by Rafal on 13.04.2021.
 */
val pokemonModule = module {

  factory<AllPokemonToPokemonMapper> {
    AllPokemonToPokemonMapperImpl()
  }

  single<PokemonRepository> {
    PokemonRepositoryImpl(
      apolloClient = get(),
      allPokemonToPokemonMapper = get(),
    )
  }

  factory<InitRepositoryUseCase> {
    InitRepositoryUseCaseImpl(
      pokemonRepository = get()
    )
  }

  single<FavouritesRepository> {
    FavouritesRepositoryImpl(
      sharedPreferencesRepository = get(),
      jsonSerializer = get(),
    )
  }

  factory<SearchByNameUseCase> {
    SearchByNameUseCaseImpl(
      pokemonRepository = get(),
    )
  }

  factory<PrepareCacheUseCase> {
    PrepareCacheUseCaseImpl(
      loadFavouritesUseCase = get(),
    )
  }

  factory<LoadFavouritesUseCase> {
    LoadFavouritesUseCaseImpl(
      favouritesRepository = get(),
      pokemonRepository = get(),
    )
  }

  factory<ChangeIsHeroFavouriteUseCase> {
    ChangeIsHeroFavouriteUseCaseImpl(
      favouritesRepository = get(),
      pokemonRepository = get(),
    )
  }


}