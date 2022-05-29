package com.glowka.rafal.pokemon.presentation.flow.intro

import com.glowka.rafal.pokemon.domain.utils.EmptyParam
import com.glowka.rafal.pokemon.presentation.architecture.ScreenStructure
import org.koin.core.scope.Scope

object IntroScreenStructure : ScreenStructure<EmptyParam, IntroViewModelToFlowInterface.Event,
    IntroViewModelToFlowInterface, IntroViewModelToViewInterface>(
  statusBarColor = 0xFFFFFFFF.toInt(),
  lightTextColor = false,
) {
  override val fragmentClass = IntroFragment::class
  override fun Scope.viewModelCreator() = IntroViewModelImpl(
    initRepositoryUseCase = get(),
    snackBarService = get(),
  )
}