package com.glowka.rafal.pokemon.domain.usecase

import com.glowka.rafal.pokemon.domain.service.ToastService
import com.glowka.rafal.pokemon.domain.utils.logE
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

class CoroutineErrorHandler(
  val toastService: ToastService,
) : AbstractCoroutineContextElement(CoroutineExceptionHandler), CoroutineExceptionHandler {
  override fun handleException(context: CoroutineContext, exception: Throwable) {
    exception.message?.let { message ->
      toastService.showMessage(message)
    }
    logE("Coroutine exception", exception)
  }
}