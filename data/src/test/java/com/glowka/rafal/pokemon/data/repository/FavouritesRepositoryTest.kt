package com.glowka.rafal.pokemon.data.repository

import android.content.SharedPreferences
import com.glowka.rafal.pokemon.data.remote.JSONSerializer
import com.glowka.rafal.pokemon.data.repository.sharedpreferences.SharedPreferencesRepository
import com.glowka.rafal.pokemon.domain.repository.FavouritesRepository
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Assert
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Created by Rafal on 27.04.2021.
 */
class FavouritesRepositoryTest {

  @MockK
  private lateinit var sharedPreferencesRepository: SharedPreferencesRepository

  @MockK
  private lateinit var sharedPreferences: SharedPreferences

  @MockK
  private lateinit var jsonSerializer: JSONSerializer

  lateinit var repository: FavouritesRepository

  @Before
  fun prepare() {
    MockKAnnotations.init(this)

    every { sharedPreferencesRepository.getFavouritesSharedPreferences() } returns sharedPreferences

    repository = FavouritesRepositoryImpl(
      sharedPreferencesRepository = sharedPreferencesRepository,
      jsonSerializer = jsonSerializer,
    )
  }

  @After
  fun finish() {
    confirmVerified(
      sharedPreferencesRepository,
      jsonSerializer
    )
    unmockkAll()
    clearAllMocks()
  }

  @Test
  fun readingDataFromSharedPreferences() = runBlocking {
    // Given
    val JSONDATA = "[123, 124, 125]"
    val parserData = FavouritesRepositoryImpl.FavouritesFieldType()

    every {
      sharedPreferences.getString(
        FavouritesRepositoryImpl.FIELD_FAVOURITES,
        null
      )
    } returns JSONDATA
    every {
      jsonSerializer.fromJSON<FavouritesRepositoryImpl.FavouritesFieldType>(
        JSONDATA,
        FavouritesRepositoryImpl.FavouritesFieldType::class.java
      )
    } returns parserData

    // When
    val response = repository.loadFavorites().first()

    // Then
    Assert.assertEquals(parserData, response)
    verify { sharedPreferencesRepository.getFavouritesSharedPreferences() }
    verify { sharedPreferences.getString(FavouritesRepositoryImpl.FIELD_FAVOURITES, null) }
    verify {
      jsonSerializer.fromJSON<FavouritesRepositoryImpl.FavouritesFieldType>(
        JSONDATA,
        FavouritesRepositoryImpl.FavouritesFieldType::class.java
      )
    }
  }

  @Test
  fun savingDataToSharedPreferences() = runBlocking {
    // Given
    val favourites = listOf(123, 124, 125)
    val jsonFavourites = "[123, 124, 125]"
    val editor: SharedPreferences.Editor = mockk()
    every { jsonSerializer.toJSON(favourites) } returns jsonFavourites
    every { sharedPreferences.edit() } returns editor
    every {
      editor.putString(
        FavouritesRepositoryImpl.FIELD_FAVOURITES,
        jsonFavourites
      )
    } returns editor
    every { editor.commit() } returns true

    // When
    repository.saveFavourites(favourities = favourites)

    //Then
    verify { sharedPreferencesRepository.getFavouritesSharedPreferences() }
    verify { jsonSerializer.toJSON(favourites) }
    verify { sharedPreferences.edit() }
  }

}