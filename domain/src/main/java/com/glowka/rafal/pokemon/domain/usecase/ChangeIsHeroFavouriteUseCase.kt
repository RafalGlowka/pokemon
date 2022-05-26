package com.glowka.rafal.pokemon.domain.usecase

import com.glowka.rafal.pokemon.domain.model.Pokemon
import com.glowka.rafal.pokemon.domain.repository.FavouritesRepository
import com.glowka.rafal.pokemon.domain.repository.PokemonRepository
import com.glowka.rafal.pokemon.domain.usecase.ChangeIsHeroFavouriteUseCase.Param
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Rafal on 17.04.2021.
 */
interface ChangeIsHeroFavouriteUseCase : UseCase<Param, List<Pokemon>> {
  data class Param(
    val pokemon: Pokemon,
    val isFavourite: Boolean
  )
}

/**
 * Cache is helping with finding favourite heroes and we do not need to startAsync coroutines to get them.
 */

class ChangeIsHeroFavouriteUseCaseImpl(
  private val favouritesRepository: FavouritesRepository,
  private val pokemonRepository: PokemonRepository,
) : ChangeIsHeroFavouriteUseCase {

  override fun invoke(param: Param): Flow<UseCaseResult<List<Pokemon>>> {
    return favouritesRepository.loadFavorites().map { list ->
      val newList = list.toMutableList()
      if (param.isFavourite) {
        if (newList.contains(param.pokemon.id).not()) {
          newList.add(param.pokemon.id)
        }
      } else {
        newList.remove(param.pokemon.id)
      }
      newList.toList()
    }.map { newList ->
      favouritesRepository.saveFavourites(newList)

      val pokemons = newList.map { pokemonId ->
        pokemonRepository.searchById(id = pokemonId)
      }
      UseCaseResult.Success(data = pokemons.filterNotNull())
    }
  }
}