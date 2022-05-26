package com.glowka.rafal.pokemon.modules.flow

import com.glowka.rafal.pokemon.presentation.architecture.businessFlow
import com.glowka.rafal.pokemon.presentation.architecture.screen
import com.glowka.rafal.pokemon.presentation.flow.intro.IntroFlow
import com.glowka.rafal.pokemon.presentation.flow.intro.IntroFlowImpl
import org.koin.dsl.module

/**
 * Created by Rafal on 14.04.2021.
 */
val introFeatureModule = module {

  single<IntroFlow> {
    IntroFlowImpl(dashboardFlow = get())
  }

  businessFlow(
    scopeName = IntroFlow.SCOPE_NAME,
  ) {
    screen(screen = IntroFlow.Screens.Start)
  }

}