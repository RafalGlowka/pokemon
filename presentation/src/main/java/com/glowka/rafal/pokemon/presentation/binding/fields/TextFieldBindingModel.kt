package com.glowka.rafal.pokemon.presentation.binding.fields

import com.glowka.rafal.pokemon.domain.utils.EMPTY
import com.glowka.rafal.pokemon.presentation.binding.models.TextBindingModel
import com.glowka.rafal.pokemon.presentation.binding.models.TextBindingModelImpl

/**
 * Created by Rafal on 17.04.2021.
 */

interface TextFieldBindingModel {
  val label: TextBindingModel
  val value: TextBindingModel
}

class TextFieldBindingModelImpl(
  initialLabel: String = String.EMPTY,
  initialValue: String = String.EMPTY
) : TextFieldBindingModel {
  override val label = TextBindingModelImpl(initialText = initialLabel)
  override val value = TextBindingModelImpl(initialText = initialValue)
}