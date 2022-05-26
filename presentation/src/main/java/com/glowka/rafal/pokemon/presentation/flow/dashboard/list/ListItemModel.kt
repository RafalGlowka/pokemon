package com.glowka.rafal.pokemon.presentation.flow.dashboard.list

import com.glowka.rafal.pokemon.domain.model.Pokemon
import com.glowka.rafal.pokemon.presentation.R
import com.glowka.rafal.pokemon.presentation.binding.ImageModel
import com.glowka.rafal.pokemon.presentation.binding.models.ImageBindingModel
import com.glowka.rafal.pokemon.presentation.binding.models.ImageBindingModelImpl

/**
 * Created by Rafal on 15.04.2021.
 */
sealed class ListItemModel {
  class HeroCard(val pokemon: Pokemon, val isFavourite: Boolean) : ListItemModel() {
    val heroName = pokemon.name
    val image: ImageBindingModel = ImageBindingModelImpl(
      initialImage = ImageModel(
        url = pokemon.imageFront,
        fallbackImageResId = R.drawable.hero_fallback
      )
    )
    val favIcon: ImageBindingModel = ImageBindingModelImpl(
      initialImage = ImageModel(
        fallbackImageResId = R.drawable.ic_favorite_on
      )
    ).apply {
      visible.postValue(isFavourite)
    }
  }
}