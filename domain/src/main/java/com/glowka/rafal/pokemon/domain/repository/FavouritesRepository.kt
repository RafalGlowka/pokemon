package com.glowka.rafal.pokemon.domain.repository

import kotlinx.coroutines.flow.Flow

/**
 * Created by Rafal on 17.04.2021.
 */
interface FavouritesRepository {
  fun loadFavorites(): Flow<List<Int>>
  fun saveFavourites(favourities: List<Int>)
}