package com.glowka.rafal.pokemon.presentation.binding.fields

import com.glowka.rafal.pokemon.domain.utils.EMPTY
import com.glowka.rafal.pokemon.presentation.R
import com.glowka.rafal.pokemon.presentation.binding.ImageModel
import com.glowka.rafal.pokemon.presentation.binding.models.ImageBindingModel
import com.glowka.rafal.pokemon.presentation.binding.models.ImageBindingModelImpl
import com.glowka.rafal.pokemon.presentation.binding.models.TextBindingModel
import com.glowka.rafal.pokemon.presentation.binding.models.TextBindingModelImpl

interface HeaderFieldBindingModel {
  val label: TextBindingModel
  val favIcon: ImageBindingModel
  val closeIcon: ImageBindingModel
}

class HeaderFieldBindingModelImpl(initialLabel: String = String.EMPTY) : HeaderFieldBindingModel {
  override val label = TextBindingModelImpl(initialText = initialLabel)
  override val favIcon = ImageBindingModelImpl().apply { visible.postValue(false) }
  override val closeIcon = ImageBindingModelImpl(
    initialImage = ImageModel(fallbackImageResId = R.drawable.ic_close)
  )
}