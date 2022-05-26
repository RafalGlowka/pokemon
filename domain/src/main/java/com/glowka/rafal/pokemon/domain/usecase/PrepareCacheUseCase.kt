package com.glowka.rafal.pokemon.domain.usecase

import com.glowka.rafal.pokemon.domain.utils.EmptyParam
import kotlinx.coroutines.flow.Flow
import com.glowka.rafal.pokemon.domain.utils.mapSuccess

/**
 * Created by Rafal on 17.04.2021.
 */
interface PrepareCacheUseCase : UseCase<EmptyParam, Unit>

class PrepareCacheUseCaseImpl(
  private val loadFavouritesUseCase: LoadFavouritesUseCase
) : PrepareCacheUseCase {

  override fun invoke(param: EmptyParam): Flow<UseCaseResult<Unit>> {
    return loadFavouritesUseCase(param = EmptyParam.EMPTY).mapSuccess { Unit }
  }
}