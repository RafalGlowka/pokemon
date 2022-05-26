package com.glowka.rafal.pokemon.presentation.flow.dashboard.list

import com.glowka.rafal.pokemon.domain.utils.EmptyParam
import com.glowka.rafal.pokemon.presentation.architecture.ScreenStructure
import org.koin.core.scope.Scope

object ListScreenStructure : ScreenStructure<EmptyParam, ListViewModelToFlowInterface.Event,
      ListViewModelToFlowInterface, ListViewModelToViewInterface>() {
  override val fragmentClass = ListFragment::class
  override fun Scope.viewModelCreator() = ListViewModelImpl(
    stringResolver = get(),
    loadFavouritesUseCase = get(),
    searchByNameUseCase = get(),
  )

}