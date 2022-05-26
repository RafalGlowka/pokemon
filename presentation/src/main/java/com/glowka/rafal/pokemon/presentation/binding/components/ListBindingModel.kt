package com.glowka.rafal.pokemon.presentation.binding.components

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface ListBindingModel<BINDING_MODEL> {
  val items: LiveData<List<BINDING_MODEL>>
}

class ListBindingModelImpl<BINDING_MODEL>(
  items: List<BINDING_MODEL> = emptyList()
) : ListBindingModel<BINDING_MODEL> {
  override val items = MutableLiveData<List<BINDING_MODEL>>(items)
}