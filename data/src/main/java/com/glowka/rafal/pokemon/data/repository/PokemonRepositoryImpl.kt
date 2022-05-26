package com.glowka.rafal.pokemon.data.repository

import com.apollographql.apollo3.ApolloClient
import com.glowka.rafal.pokemon.data.graphql.PokemonListQuery
import com.glowka.rafal.pokemon.data.mapper.AllPokemonToPokemonMapper
import com.glowka.rafal.pokemon.data.utils.toUseCaseResultFlow
import com.glowka.rafal.pokemon.domain.model.Pokemon
import com.glowka.rafal.pokemon.domain.repository.PokemonRepository
import com.glowka.rafal.pokemon.domain.usecase.UseCaseResult
import com.glowka.rafal.pokemon.domain.utils.mapSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by Rafal on 14.04.2021.
 */
class PokemonRepositoryImpl(
  private val apolloClient: ApolloClient,
  private val allPokemonToPokemonMapper: AllPokemonToPokemonMapper,
) : PokemonRepository {

  private var pokemons: List<Pokemon> = emptyList()

  override fun indexPokemons(): Flow<UseCaseResult<List<Pokemon>>> {
    return apolloClient.query(PokemonListQuery()).toUseCaseResultFlow().mapSuccess { data ->
      pokemons = data.allPokemon?.mapNotNull { pokemon ->
        allPokemonToPokemonMapper(pokemon!!)
      } ?: emptyList()
      pokemons
    }
  }

  override fun query(query: String): Flow<UseCaseResult<List<Pokemon>>> {
    return flow {
      emit(UseCaseResult.Success(pokemons.filter { pokemon -> pokemon.name.contains(query) }))
    }
  }

  override fun searchById(id: Int): Pokemon? {
    return pokemons.firstOrNull { pokemon -> pokemon.id == id }
  }

}

