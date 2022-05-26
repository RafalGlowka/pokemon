package com.glowka.rafal.pokemon.presentation

import android.os.Bundle
import com.glowka.rafal.pokemon.domain.utils.EmptyParam
import com.glowka.rafal.pokemon.domain.utils.inject
import com.glowka.rafal.pokemon.presentation.architecture.BaseActivity
import com.glowka.rafal.pokemon.presentation.flow.intro.IntroFlow
import com.glowka.rafal.pokemon.presentation.flow.intro.IntroResult
import com.glowka.rafal.pokemon.presentation.utils.exhaustive
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {

  val scope = CoroutineScope(Dispatchers.Main)

  val introFlow: IntroFlow by inject()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    scope.launch {
      introFlow.start(
        navigator = navigator,
        param = EmptyParam.EMPTY,
      ) { result ->
        when (result) {
          IntroResult.Terminated -> finish()
        }.exhaustive
      }
    }
  }

  override fun onDestroy() {
    scope.coroutineContext.cancel()
    super.onDestroy()
  }
}