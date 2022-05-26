package com.glowka.rafal.pokemon.domain.usecase

import kotlinx.coroutines.flow.Flow

/**
 * Created by Rafal on 14.04.2021.
 */

sealed class UseCaseResult<out DATA : Any?> {
  data class Error(val message: String) : UseCaseResult<Nothing>()
  data class Success<out DATA : Any?>(val data: DATA) : UseCaseResult<DATA>()
}

interface UseCase<PARAM, RESULT> {
  operator fun invoke(
    param: PARAM,
  ): Flow<UseCaseResult<RESULT>>
}