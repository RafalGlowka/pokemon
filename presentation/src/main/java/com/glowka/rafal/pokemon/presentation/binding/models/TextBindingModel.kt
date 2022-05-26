package com.glowka.rafal.pokemon.presentation.binding.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.glowka.rafal.pokemon.domain.utils.EMPTY

/**
 * Created by Rafal on 17.04.2021.
 */
interface TextBindingModel {
  val text: LiveData<String>
  val visible: LiveData<Boolean>
}

class TextBindingModelImpl(initialText: String = String.EMPTY) : TextBindingModel {
  override val text = MutableLiveData(initialText)
  override val visible = MutableLiveData(true)
}