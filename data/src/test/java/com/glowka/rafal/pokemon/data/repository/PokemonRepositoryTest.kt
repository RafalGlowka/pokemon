package com.glowka.rafal.pokemon.data.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Error
import com.glowka.rafal.pokemon.data.graphql.PokemonListQuery
import com.glowka.rafal.pokemon.data.mapper.AllPokemonToPokemonMapper
import com.glowka.rafal.pokemon.data.utils.returnsData
import com.glowka.rafal.pokemon.data.utils.returnsError
import com.glowka.rafal.pokemon.domain.model.Pokemon
import com.glowka.rafal.pokemon.domain.repository.PokemonRepository
import com.glowka.rafal.pokemon.domain.usecase.UseCaseResult
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Assert
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Created by Rafal on 27.05.2022.
 */
class PokemonRepositoryTest {

  @MockK
  private lateinit var apolloClient: ApolloClient

  @MockK
  private lateinit var allPokemonToPokemonMapper: AllPokemonToPokemonMapper

  lateinit var repository: PokemonRepository

  @Before
  fun prepare() {
    MockKAnnotations.init(this)
    repository = PokemonRepositoryImpl(
      apolloClient = apolloClient,
      allPokemonToPokemonMapper = allPokemonToPokemonMapper,
    )
  }

  @After
  fun finish() {
    confirmVerified(
      apolloClient,
      allPokemonToPokemonMapper,
    )
    unmockkAll()
    clearAllMocks()
  }

  @Test
  fun checkErrorHandling() = runBlocking {
    // Given
    val TEST_MESSAGE = "Test message"
    val error = Error(
      message = TEST_MESSAGE,
      null,
      null,
      null,
      null,
    )

    every { apolloClient.query<PokemonListQuery.Data>(any()) } returnsError error
    val response = repository.indexPokemons().first()


    // Then
    Assert.assertEquals(UseCaseResult.Error(message = TEST_MESSAGE), response)
    verify { apolloClient.query<PokemonListQuery.Data>(any()) }
  }

  @Test
  fun checkingInitializationAndSearch() = runBlocking {
    // Given
    val POKEMON_ID = 123
    val POKEMON_NAME = "name"
    val remotePokemon = PokemonListQuery.AllPokemon(
      id = POKEMON_ID,
      name = POKEMON_NAME,
      games = emptyList(),
      sprites = PokemonListQuery.Sprites(
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
      ),
      abilities = emptyList(),
    )
    val pokemon = Pokemon(
      id = POKEMON_ID,
      name = POKEMON_NAME,
      games = emptyList(),
      imageFront = null,
      imageFrontFemale = null,
      imageBackFemale = null,
      imageBack = null,
      abilities = emptyList()
    )

    every { allPokemonToPokemonMapper.invoke(remotePokemon) } returns pokemon

    val remoteData: PokemonListQuery.Data = mockk()
    every { remoteData.allPokemon } returns listOf(remotePokemon)

    every { apolloClient.query<PokemonListQuery.Data>(any()) } returnsData remoteData
    println(repository.indexPokemons().first())

    // When
    val response = repository.searchById(POKEMON_ID)

    // Then
    Assert.assertEquals(pokemon, response)
    verify { apolloClient.query<PokemonListQuery.Data>(any()) }
    verify { allPokemonToPokemonMapper(remotePokemon) }
  }

}