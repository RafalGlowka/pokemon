package com.glowka.rafal.pokemon.presentation.flow.intro

import com.glowka.rafal.pokemon.domain.service.SnackBarService
import com.glowka.rafal.pokemon.domain.usecase.InitRepositoryUseCase
import com.glowka.rafal.pokemon.domain.utils.EmptyParam
import com.glowka.rafal.pokemon.domain.utils.collectUseCase
import com.glowka.rafal.pokemon.domain.utils.logD
import com.glowka.rafal.pokemon.domain.utils.onMain
import com.glowka.rafal.pokemon.presentation.architecture.*
import com.glowka.rafal.pokemon.presentation.flow.intro.IntroViewModelToFlowInterface.Event
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Rafal on 13.04.2021.
 */

interface IntroViewModelToFlowInterface : ViewModelToFlowInterface<EmptyParam, Event> {
  sealed class Event : ScreenEvent {
    object Finished : Event()
  }
}

interface IntroViewModelToViewInterface : ViewModelToViewInterface {
}

class IntroViewModelImpl(
  private val snackBarService: SnackBarService,
  private val initRepositoryUseCase: InitRepositoryUseCase
) : IntroViewModelToFlowInterface, IntroViewModelToViewInterface, BaseViewModel<EmptyParam, Event>(
  backPressedEvent = null
) {

  override fun init(param: EmptyParam) {
    initialDataFeatch()
  }

  private fun initialDataFeatch() {
    launch {
      initRepositoryUseCase(param = EmptyParam.EMPTY).collectUseCase(
        onSuccess = { list ->
          logD("received ${list.size} pokemons")
          onMain {
            showNext()
          }
        },
        onError = { error ->
          // Error decoding should be added to show user a call to action message.
          snackBarService.showSnackBar(
            message = error.message,
            duration = Snackbar.LENGTH_INDEFINITE,
            actionLabel = "Retry",
            action = {
              initialDataFeatch()
            }
          )
        }
      )
    }
  }

  private fun showNext() {
    sendEvent(event = Event.Finished)
  }
}