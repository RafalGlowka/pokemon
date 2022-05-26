package com.glowka.rafal.pokemon.presentation.flow.dashboard.details

import com.glowka.rafal.pokemon.domain.model.Pokemon
import com.glowka.rafal.pokemon.domain.usecase.ChangeIsHeroFavouriteUseCase
import com.glowka.rafal.pokemon.domain.utils.StringResolver
import com.glowka.rafal.pokemon.domain.utils.logD
import com.glowka.rafal.pokemon.presentation.R
import com.glowka.rafal.pokemon.presentation.architecture.*
import com.glowka.rafal.pokemon.presentation.binding.ImageModel
import com.glowka.rafal.pokemon.presentation.binding.components.ListBindingModel
import com.glowka.rafal.pokemon.presentation.binding.components.ListBindingModelImpl
import com.glowka.rafal.pokemon.presentation.binding.fields.*
import com.glowka.rafal.pokemon.presentation.binding.models.ImageBindingModel
import com.glowka.rafal.pokemon.presentation.binding.models.ImageBindingModelImpl
import com.glowka.rafal.pokemon.presentation.binding.models.TextBindingModel
import com.glowka.rafal.pokemon.presentation.binding.models.TextBindingModelImpl
import com.glowka.rafal.pokemon.presentation.flow.dashboard.details.DetailsViewModelToFlowInterface.Event
import com.glowka.rafal.pokemon.presentation.flow.dashboard.details.DetailsViewModelToFlowInterface.Param
import kotlinx.coroutines.flow.collect

/**
 * Created by Rafal on 13.04.2021.
 */

interface DetailsViewModelToFlowInterface : ViewModelToFlowInterface<Param, Event> {
  data class Param(val pokemon: Pokemon, val isFavourite: Boolean)
  sealed class Event : ScreenEvent {
    object Back : Event()
  }
}

interface DetailsViewModelToViewInterface : ViewModelToViewInterface {
  val header: HeaderFieldBindingModel
  val image: ImageBindingModel
  val fullName: TextBindingModel
  val games: TextFieldBindingModel
  val abilities: TextFieldBindingModel
  val galeryLabel: TextFieldBindingModel
  val images: ListBindingModel<ImageModel>
}

class DetailsViewModelImpl(
  private val stringResolver: StringResolver,
  private val changeIsHeroFavouriteUseCase: ChangeIsHeroFavouriteUseCase,
) : DetailsViewModelToViewInterface, DetailsViewModelToFlowInterface,
  BaseViewModel<Param, Event>(
    backPressedEvent = Event.Back
  ) {

  override val header = HeaderFieldBindingModelImpl().apply { favIcon.visible.postValue(true) }
  override val image = ImageBindingModelImpl()
  override val fullName = TextBindingModelImpl()
  override val games = TextFieldBindingModelImpl(
    initialLabel = stringResolver(R.string.label_games)
  )
  override val abilities = TextFieldBindingModelImpl(
    initialLabel = stringResolver(R.string.label_abilities)
  )
  override val galeryLabel = TextFieldBindingModelImpl(
    initialLabel = stringResolver(R.string.label_gallery)
  )
  override val images = ListBindingModelImpl<ImageModel>()

  lateinit var param: Param

  override fun init(param: Param) {
    this.param = param
    setHeader(param = param)
    setBasicData(pokemon = param.pokemon)
    images.items.postValue(
      listOfNotNull(
        param.pokemon.imageFront,
        param.pokemon.imageFrontFemale,
        param.pokemon.imageBack,
        param.pokemon.imageBackFemale
      ).filter { url -> url.isNotBlank() }.map { image ->
        ImageModel(url = image, fallbackImageResId = R.drawable.exclamation)
      }
    )
    logD("${images.items.value?.size}")
  }

  private fun setHeader(param: Param) {
    header.label.text.postValue(param.pokemon.name)
    header.closeIcon.action.postValue(::closeScreen)
    val favIconResId = if (param.isFavourite) {
      R.drawable.ic_favorite_on
    } else {
      R.drawable.ic_favorite_off
    }
    header.favIcon.image.postValue(ImageModel(fallbackImageResId = favIconResId))
    header.favIcon.action.postValue(::onFavouriteClick)
  }

  private fun setBasicData(pokemon: Pokemon) {
    fullName.text.postValue(pokemon.name)
    image.image.postValue(
      ImageModel(
        url = pokemon.imageFront,
        fallbackImageResId = R.drawable.hero_fallback
      )
    )
    var gamesLabel = ""
    pokemon.games.forEach { game ->
      gamesLabel += game.name + ", "
    }
    games.value.text.postValue(gamesLabel)
    var abs = ""
    pokemon.abilities.forEach { ab ->
      abs += "$ab, "
    }
    abilities.value.text.postValue(abs)
  }

  private fun onFavouriteClick() {
    param = Param(pokemon = param.pokemon, isFavourite = !param.isFavourite)
    val favIconResId = if (param.isFavourite) {
      R.drawable.ic_favorite_on
    } else {
      R.drawable.ic_favorite_off
    }

    launch {
      header.favIcon.image.postValue(ImageModel(fallbackImageResId = favIconResId))
      changeIsHeroFavouriteUseCase(
        param = ChangeIsHeroFavouriteUseCase.Param(
          pokemon = param.pokemon,
          isFavourite = param.isFavourite
        )
      ).collect { _ -> }
    }
  }

  private fun closeScreen() {
    launch {
      sendEvent(event = Event.Back)
    }
  }

}