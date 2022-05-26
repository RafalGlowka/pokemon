package com.glowka.rafal.pokemon.domain.usecase

import com.glowka.rafal.pokemon.domain.model.Pokemon
import com.glowka.rafal.pokemon.domain.repository.PokemonRepository
import com.glowka.rafal.pokemon.domain.utils.EmptyParam
import kotlinx.coroutines.flow.Flow

/**
 * Created by Rafal on 17.04.2021.
 */
interface InitRepositoryUseCase : UseCase<EmptyParam, List<Pokemon>>

class InitRepositoryUseCaseImpl(
  val pokemonRepository: PokemonRepository,
) : InitRepositoryUseCase {

  override fun invoke(param: EmptyParam): Flow<UseCaseResult<List<Pokemon>>> {
    return pokemonRepository.indexPokemons()
  }
}