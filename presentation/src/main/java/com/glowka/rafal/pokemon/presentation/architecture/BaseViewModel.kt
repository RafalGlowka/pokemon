package com.glowka.rafal.pokemon.presentation.architecture

import androidx.lifecycle.LifecycleOwner
import com.glowka.rafal.pokemon.domain.usecase.CoroutineErrorHandler
import com.glowka.rafal.pokemon.domain.utils.EmptyParam
import com.glowka.rafal.pokemon.domain.utils.inject
import com.glowka.rafal.pokemon.domain.utils.logE
import kotlinx.coroutines.*
import java.io.Closeable
import kotlin.coroutines.CoroutineContext

/**
 * Created by Rafal on 13.04.2021.
 */

interface ViewModelToViewInterface {
  fun onBackPressed(): Boolean
}

interface ViewModelToFlowInterface<PARAM : Any, EVENT : ScreenEvent> {
  var onScreenEvent: (EVENT) -> Unit

  fun init(param: PARAM)

  fun clear()
}

interface ViewModelInterface<PARAM : Any, EVENT : ScreenEvent> :
  ViewModelToViewInterface,
  ViewModelToFlowInterface<PARAM, EVENT>

abstract class BaseViewModel<PARAM : Any, EVENT : ScreenEvent>(private val backPressedEvent: EVENT?) :
  ViewModelInterface<PARAM, EVENT> {

  override lateinit var onScreenEvent: (EVENT) -> Unit

  lateinit var lifecycleOwner: LifecycleOwner
  private var _viewModelScope: CloseableCoroutineScope? = null
  val viewModelScope: CoroutineScope
    get() {
      if (_viewModelScope == null) {
        _viewModelScope = CloseableCoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
      }
      return _viewModelScope!!
    }

  override fun init(param: PARAM) {
    if (param !is EmptyParam) logE("Function init(param) should be overrided")
  }

  protected fun sendEvent(event: EVENT) {
    onScreenEvent(event)
  }

  override fun onBackPressed(): Boolean {
    return backPressedEvent?.let { event ->
      launch {
        sendEvent(event)
      }
      true
    } ?: false
  }

  override fun clear() {
    _viewModelScope?.close()
    _viewModelScope = null
  }
}

internal class CloseableCoroutineScope(context: CoroutineContext) : Closeable, CoroutineScope {
  override val coroutineContext: CoroutineContext = context

  override fun close() {
    coroutineContext.cancel()
  }
}

fun BaseViewModel<*, *>.launch(
  context: CoroutineContext? = null,
  start: CoroutineStart = CoroutineStart.DEFAULT,
  block: suspend CoroutineScope.() -> Unit
): Job {
  var coroutineContext = context
  if (coroutineContext == null) {
    val coroutineErrorHandler: CoroutineErrorHandler by inject()
    coroutineContext = coroutineErrorHandler
  }
  return viewModelScope.launch(coroutineContext, start, block)
}