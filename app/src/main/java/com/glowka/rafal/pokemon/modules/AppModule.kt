package com.glowka.rafal.pokemon.modules

import com.glowka.rafal.pokemon.data.repository.sharedpreferences.SharedPreferencesRepository
import com.glowka.rafal.pokemon.data.repository.sharedpreferences.SharedPreferencesRepositoryImpl
import com.glowka.rafal.pokemon.domain.service.SnackBarService
import com.glowka.rafal.pokemon.presentation.service.ToastServiceImpl
import com.glowka.rafal.pokemon.domain.service.ToastService
import com.glowka.rafal.pokemon.domain.usecase.CoroutineErrorHandler
import com.glowka.rafal.pokemon.domain.utils.StringResolver
import com.glowka.rafal.pokemon.presentation.service.SnackBarServiceImpl
import com.glowka.rafal.pokemon.presentation.utils.StringResolverImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Created by Rafal on 13.04.2021.
 */
val appModule = module {

  single<StringResolver> {
    StringResolverImpl(
      context = androidContext()
    )
  }

  single<SharedPreferencesRepository> {
    SharedPreferencesRepositoryImpl(
      context = androidContext()
    )
  }

  single<ToastService> {
    ToastServiceImpl(
      context = androidContext()
    )
  }

  single<SnackBarService> {
    SnackBarServiceImpl()
  }

  single<CoroutineErrorHandler> {
    CoroutineErrorHandler(toastService = get())
  }
}