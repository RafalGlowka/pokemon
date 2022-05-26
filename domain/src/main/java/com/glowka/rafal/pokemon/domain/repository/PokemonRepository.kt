package com.glowka.rafal.pokemon.domain.repository

import com.glowka.rafal.pokemon.domain.model.Pokemon
import com.glowka.rafal.pokemon.domain.usecase.UseCaseResult
import kotlinx.coroutines.flow.Flow

/**
 * Created by Rafal on 14.04.2021.
 */
interface PokemonRepository {
  fun indexPokemons(): Flow<UseCaseResult<List<Pokemon>>>
  fun query(query: String): Flow<UseCaseResult<List<Pokemon>>>
  fun searchById(id: Int): Pokemon?
}