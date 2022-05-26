package com.glowka.rafal.pokemon.domain.usecase

import com.glowka.rafal.pokemon.domain.model.Pokemon
import com.glowka.rafal.pokemon.domain.utils.EmptyParam
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
class PrepareCacheUseCaseTest {

  @MockK
  private lateinit var loadFavouriteUseCase: LoadFavouritesUseCase

  lateinit var useCase: PrepareCacheUseCase

  @Before
  fun prepare() {
    MockKAnnotations.init(this)
    useCase = PrepareCacheUseCaseImpl(
      loadFavouritesUseCase = loadFavouriteUseCase
    )
  }

  @After
  fun finish() {
    confirmVerified(loadFavouriteUseCase)

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
    every { loadFavouriteUseCase.invoke(param = EmptyParam.EMPTY) } returns
        flowOf(UseCaseResult.Success(listOf(pokemon)))

    // When
    val response = useCase(param = params).first()

    // Than
    Assert.assertEquals(UseCaseResult.Success(Unit), response)
    verify { loadFavouriteUseCase.invoke(param = EmptyParam.EMPTY) }
  }
}