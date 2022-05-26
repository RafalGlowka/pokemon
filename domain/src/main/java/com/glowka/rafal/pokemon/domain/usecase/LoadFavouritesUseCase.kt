package com.glowka.rafal.pokemon.domain.usecase

import com.glowka.rafal.pokemon.domain.model.Pokemon
import com.glowka.rafal.pokemon.domain.repository.FavouritesRepository
import com.glowka.rafal.pokemon.domain.repository.PokemonRepository
import com.glowka.rafal.pokemon.domain.utils.EmptyParam
import kotlinx.coroutines.flow.*

/**
 * Created by Rafal on 17.04.2021.
 */
interface LoadFavouritesUseCase : UseCase<EmptyParam, List<Pokemon>>

class LoadFavouritesUseCaseImpl(
  private val favouritesRepository: FavouritesRepository,
  private val pokemonRepository: PokemonRepository,
) : LoadFavouritesUseCase {

  override fun invoke(param: EmptyParam): Flow<UseCaseResult<List<Pokemon>>> {
    return favouritesRepository.loadFavorites().map { list ->
        UseCaseResult.Success(list
          .map { heroId ->
            pokemonRepository.searchById(id = heroId)
          }
          .toList()
          .filterNotNull())
      }
    }
}