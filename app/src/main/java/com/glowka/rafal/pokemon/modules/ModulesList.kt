package com.glowka.rafal.pokemon.modules

import com.glowka.rafal.pokemon.modules.flow.dashboardFeatureModule
import com.glowka.rafal.pokemon.modules.flow.introFeatureModule
import org.koin.core.module.Module

/**
 * Created by Rafal on 13.04.2021.
 */

val modulesList = listOf<Module>(
  appModule,
  remoteModule,
  pokemonModule,
  introFeatureModule,
  dashboardFeatureModule
)