package com.glowka.rafal.pokemon.presentation.binding.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.glowka.rafal.pokemon.domain.utils.EMPTY

/**
 * Created by Rafal on 15.04.2021.
 */

interface EditTextBindingModel {
  val text: MutableLiveData<String>
  val hint: LiveData<String>
}

class EditTextBindingModelImpl(
  initialText: String = String.EMPTY,
  initialHint: String = String.EMPTY
) : EditTextBindingModel {
  override val text = MutableLiveData(initialText)
  override val hint = MutableLiveData(initialHint)
}