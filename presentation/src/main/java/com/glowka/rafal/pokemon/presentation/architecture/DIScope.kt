package com.glowka.rafal.pokemon.presentation.architecture

import com.glowka.rafal.pokemon.domain.utils.logE
import org.koin.core.context.GlobalContext
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.ScopeDSL

/**
 * Created by Rafal on 14.04.2021.
 *
 * Business flow abstraction. Business flow contains few screens and a separate DI scope.
 *
 */

fun Flow<*, *>.createScope(): Scope {
  return GlobalContext.get().getOrCreateScope(flowScopeName, named(flowScopeName))
}

fun Flow<*, *>.closeScope() {
  val scope = GlobalContext.get().getScopeOrNull(flowScopeName)
  if (scope == null) {
    logE("scope $flowScopeName do not exist !")
    return
  }
  scope.close()
}

@Suppress("NOTHING_TO_INLINE")
inline fun Module.businessFlow(
  scopeName: String,
  noinline scopeSet: ScopeDSL.() -> Unit
) {
  scope(
    named(scopeName),
    scopeSet
  )
}
