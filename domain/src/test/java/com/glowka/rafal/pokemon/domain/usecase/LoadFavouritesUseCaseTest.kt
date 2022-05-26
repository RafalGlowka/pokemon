package com.glowka.rafal.pokemon.domain.usecase

import com.glowka.rafal.pokemon.domain.model.Pokemon
import com.glowka.rafal.pokemon.domain.repository.FavouritesRepository
import com.glowka.rafal.pokemon.domain.repository.PokemonRepository
import com.glowka.rafal.pokemon.domain.utils.EmptyParam
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Assert
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Created by Rafal on 27.04.2021.
 */
class LoadFavouritesUseCaseTest {

  @MockK
  private lateinit var favouritesRepository: FavouritesRepository

  @MockK
  private lateinit var pokemonRepository: PokemonRepository

  lateinit var useCase: LoadFavouritesUseCase

  @Before
  fun prepare() {
    MockKAnnotations.init(this)
    useCase = LoadFavouritesUseCaseImpl(
      favouritesRepository = favouritesRepository,
      pokemonRepository = pokemonRepository
    )
  }

  @After
  fun finish() {
    confirmVerified(favouritesRepository, pokemonRepository)

    unmockkAll()
    clearAllMocks()
  }

  @Test
  fun useCaseIsCallingRepositories() = runBlocking {
    // Given
    val HERO_ID = 432
    val pokemon: Pokemon = mockk()
    val params = EmptyParam.EMPTY

    every { pokemon.id } returns HERO_ID
    every { favouritesRepository.loadFavorites() } returns listOf(listOf(HERO_ID)).asFlow()
    every { pokemonRepository.searchById(id = HERO_ID) } returns pokemon

    // When
    val response = useCase(param = params).first()

    // Than
    Assert.assertEquals(UseCaseResult.Success(listOf(pokemon)), response)
    verify { favouritesRepository.loadFavorites() }
    verify { pokemonRepository.searchById(id = HERO_ID) }
  }
}