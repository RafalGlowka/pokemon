package com.glowka.rafal.pokemon.modules.flow

import com.glowka.rafal.pokemon.presentation.architecture.businessFlow
import com.glowka.rafal.pokemon.presentation.architecture.screen
import com.glowka.rafal.pokemon.presentation.flow.dashboard.DashboardFlow
import com.glowka.rafal.pokemon.presentation.flow.dashboard.DashboardFlowImpl
import org.koin.dsl.module

/**
 * Created by Rafal on 14.04.2021.
 */
val dashboardFeatureModule = module {

  single<DashboardFlow> {
    DashboardFlowImpl()
  }

  businessFlow(
    scopeName = DashboardFlow.SCOPE_NAME,
  ) {
    screen(screen = DashboardFlow.Screens.List)
    screen(screen = DashboardFlow.Screens.Details)
  }

}