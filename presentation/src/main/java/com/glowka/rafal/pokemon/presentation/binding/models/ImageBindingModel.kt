package com.glowka.rafal.pokemon.presentation.binding.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.glowka.rafal.pokemon.presentation.binding.ImageModel

/**
 * Created by Rafal on 16.04.2021.
 */
interface ImageBindingModel {
  val image: LiveData<ImageModel>
  val action: LiveData<() -> Unit>
  val visible: LiveData<Boolean>
}

class ImageBindingModelImpl(
  initialImage: ImageModel = ImageModel(),
  onClickAction: () -> Unit = {}
) : ImageBindingModel {
  override val image = MutableLiveData(initialImage)
  override val action = MutableLiveData(onClickAction)
  override val visible = MutableLiveData(true)
}