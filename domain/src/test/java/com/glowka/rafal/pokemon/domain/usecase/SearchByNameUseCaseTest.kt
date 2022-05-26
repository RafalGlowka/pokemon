package com.glowka.rafal.pokemon.domain.usecase

import com.glowka.rafal.pokemon.domain.model.Pokemon
import com.glowka.rafal.pokemon.domain.repository.PokemonRepository
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Assert
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Created by Rafal on 27.04.2021.
 */
class SearchByNameUseCaseTest {

  @MockK
  private lateinit var pokemonRepository: PokemonRepository

  lateinit var useCase: SearchByNameUseCase

  @Before
  fun prepare() {
    MockKAnnotations.init(this)
    useCase = SearchByNameUseCaseImpl(
      pokemonRepository = pokemonRepository
    )
  }

  @After
  fun finish() {
    confirmVerified(pokemonRepository)

    unmockkAll()
    clearAllMocks()
  }

  @Test
  fun useCaseIsCallingRepositories() = runBlocking {
    // Given
    val query = "superman"
    val pokemon: Pokemon = mockk()

    every { pokemonRepository.query(query = query) } returns flowOf(UseCaseResult.Success(listOf(pokemon)))

    // When
    val response = useCase(param = query).first()

    // Than
    Assert.assertEquals(UseCaseResult.Success(listOf(pokemon)), response)
    verify { pokemonRepository.query(query = query) }
  }
}