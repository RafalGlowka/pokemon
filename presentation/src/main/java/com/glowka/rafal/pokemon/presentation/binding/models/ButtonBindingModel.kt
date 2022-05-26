package com.glowka.rafal.pokemon.presentation.binding.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.glowka.rafal.pokemon.domain.utils.EMPTY

/**
 * Created by Rafal on 15.04.2021.
 */
interface ButtonBindingModel {
  val label: LiveData<String>
  val iconResId: LiveData<Int>
  val action: LiveData<() -> Unit>
}

class ButtonBindingModelImpl(
  initialLabel: String = String.EMPTY,
  initialIconResId: Int = 0,
  onClickAction: () -> Unit = {}
) : ButtonBindingModel {
  override val label = MutableLiveData(initialLabel)
  override val iconResId = MutableLiveData(initialIconResId)
  override val action = MutableLiveData(onClickAction)
}