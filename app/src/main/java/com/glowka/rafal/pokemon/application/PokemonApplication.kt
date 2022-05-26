package com.glowka.rafal.pokemon.application

import android.app.Application
import com.glowka.rafal.pokemon.domain.utils.logD
import com.glowka.rafal.pokemon.modules.modulesList
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Created by Rafal on 13.04.2021.
 */
class PokemonApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    initDI()
  }

  private fun initDI() {
    startKoin {
      androidLogger()
      androidContext(this@PokemonApplication)
      modules(modulesList)
      createEagerInstances()
    }
    logD("Koin initialized")
  }
}