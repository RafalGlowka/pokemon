package com.glowka.rafal.pokemon.domain.usecase

import com.glowka.rafal.pokemon.domain.model.Pokemon
import com.glowka.rafal.pokemon.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by Rafal on 14.04.2021.
 */
interface SearchByNameUseCase : UseCase<String, List<Pokemon>>

class SearchByNameUseCaseImpl(
  private val pokemonRepository: PokemonRepository
) : SearchByNameUseCase {

  override fun invoke(param: String): Flow<UseCaseResult<List<Pokemon>>> {
    return pokemonRepository.query(query = param)
  }
}