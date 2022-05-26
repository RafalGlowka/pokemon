package com.glowka.rafal.pokemon.data.repository

import com.glowka.rafal.pokemon.data.remote.JSONSerializer
import com.glowka.rafal.pokemon.data.repository.sharedpreferences.SharedPreferencesRepository
import com.glowka.rafal.pokemon.domain.repository.FavouritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by Rafal on 17.04.2021.
 */
class FavouritesRepositoryImpl(
  sharedPreferencesRepository: SharedPreferencesRepository,
  private val jsonSerializer: JSONSerializer
) : FavouritesRepository {

  companion object {
    const val FIELD_FAVOURITES = "favourites"
    val DEFAULT_FAVOURITES = listOf(649, 78, 9)
  }

  val sharedPreferences = sharedPreferencesRepository.getFavouritesSharedPreferences()

  class FavouritesFieldType : ArrayList<Int>()

  override fun loadFavorites(): Flow<List<Int>> {
    return flow {
      val data = sharedPreferences.getString(FIELD_FAVOURITES, null)
      val result = if (data == null) {
        DEFAULT_FAVOURITES
      } else {
        jsonSerializer.fromJSON(data, FavouritesFieldType::class.java)
      }
      emit(result)
    }
  }

  override fun saveFavourites(favourities: List<Int>) {
    val data = jsonSerializer.toJSON(favourities)
    val edit = sharedPreferences.edit()
    edit.putString(FIELD_FAVOURITES, data)
    edit.commit()
  }

}