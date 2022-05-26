package com.glowka.rafal.pokemon.presentation.binding.fields

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.glowka.rafal.pokemon.domain.utils.EMPTY

/**
 * Created by Rafal on 17.04.2021.
 */

interface ProgressFieldBindingModel {
  val label: LiveData<String>
  val progress: LiveData<ProgressPosition>
}

data class ProgressPosition(val position: Int = 0, val maxPosition: Int = 100)

class ProgressFieldBindingModelImpl(
  initialLabel: String = String.EMPTY,
  initialProgress: ProgressPosition = ProgressPosition()
) : ProgressFieldBindingModel {
  override val label = MutableLiveData(initialLabel)
  override val progress = MutableLiveData(initialProgress)
}