package com.glowka.rafal.pokemon.presentation.flow.dashboard.list

import androidx.lifecycle.MutableLiveData
import com.glowka.rafal.pokemon.domain.model.Pokemon
import com.glowka.rafal.pokemon.domain.usecase.LoadFavouritesUseCase
import com.glowka.rafal.pokemon.domain.usecase.SearchByNameUseCase
import com.glowka.rafal.pokemon.domain.utils.*
import com.glowka.rafal.pokemon.presentation.R
import com.glowka.rafal.pokemon.presentation.architecture.*
import com.glowka.rafal.pokemon.presentation.binding.models.EditTextBindingModel
import com.glowka.rafal.pokemon.presentation.binding.models.EditTextBindingModelImpl
import com.glowka.rafal.pokemon.presentation.binding.models.TextBindingModel
import com.glowka.rafal.pokemon.presentation.binding.models.TextBindingModelImpl
import com.glowka.rafal.pokemon.presentation.flow.dashboard.list.ListViewModelToFlowInterface.Event
import kotlinx.coroutines.Job

/**
 * Created by Rafal on 13.04.2021.
 */

interface ListViewModelToFlowInterface : ViewModelToFlowInterface<EmptyParam, Event> {
  sealed class Event : ScreenEvent {
    class ShowDetails(val pokemon: Pokemon, val isFavourite: Boolean) : Event()
    object Back : Event()
  }

  fun refreshFavourites()
}

interface ListViewModelToViewInterface : ViewModelToViewInterface {
  val searchField: EditTextBindingModel
  val statusLabel: TextBindingModel
  val items: MutableLiveData<List<ListItemModel>>
  val message: TextBindingModel
  fun onHeroPick(heroCard: ListItemModel.HeroCard)
}

class ListViewModelImpl(
  private val stringResolver: StringResolver,
  private val loadFavouritesUseCase: LoadFavouritesUseCase,
  searchByNameUseCase: SearchByNameUseCase,
) : ListViewModelToViewInterface, ListViewModelToFlowInterface, BaseViewModel<EmptyParam, Event>(
  backPressedEvent = Event.Back
) {
  override val searchField = EditTextBindingModelImpl(
    initialHint = stringResolver(R.string.search_by_name_hint),
    initialText = stringResolver(R.string.search_by_name_initial_text)
  )
  override val items = MutableLiveData<List<ListItemModel>>()
  override val message = TextBindingModelImpl(initialText = String.EMPTY)
  override val statusLabel = TextBindingModelImpl(initialText = String.EMPTY)

  override fun onHeroPick(heroCard: ListItemModel.HeroCard) {
    launch {
      sendEvent(
        event = Event.ShowDetails(
          pokemon = heroCard.pokemon,
          isFavourite = heroCard.isFavourite
        )
      )
    }
  }

  private var searchJob: Job? = null
  private var favourites: List<Pokemon>? = null

  init {
    refreshFavourites()

    searchField.text.observeForever { newValue ->
//      searchJob?.cancel()
      searchJob = null
      if (newValue.isEmpty() || newValue.isBlank()) {
        logD("setting favs")
        favourites?.let { f ->
          updateList(pokemons = f)
          if (f.size > 0) {
            statusLabel.text.postValue(stringResolver(R.string.status_favs, "${f.size}"))
          } else {
            statusLabel.text.postValue(String.EMPTY)
          }
        } ?: logE("has no favourites")
      } else {
        searchJob = launch {
          logD("starting search for $newValue")
          searchByNameUseCase(param = newValue).collectUseCase(
            onSuccess = { list ->
              updateList(list)
//              if (list.size > 0) {
                statusLabel.text.postValue(stringResolver(R.string.status_query, "${list.size}"))
//              } else {

//              }
            }
          )
        }
      }
    }
  }

  private fun updateList(pokemons: List<Pokemon>) {
    logD("updateList ${pokemons.size}")
    if (pokemons.isEmpty()) {
      val query = searchField.text.value
      val errorMessage = if (query?.isEmpty() != false) {
        stringResolver(R.string.missing_favourites)
      } else {
        stringResolver(R.string.missing_results, query)
      }
      message.text.postValue(errorMessage)
      message.visible.postValue(true)
      items.postValue(emptyList())
    } else {
      message.visible.postValue(false)
      items.postValue(pokemons.map { pokemon ->
        ListItemModel.HeroCard(
          pokemon = pokemon,
          isFavourite = favourites?.contains(pokemon) ?: false
        )
      })
    }
  }

  override fun refreshFavourites() {
    launch {
      loadFavouritesUseCase(param = EmptyParam.EMPTY).collectUseCase(
        onSuccess = { result ->
          favourites = result
          if (searchField.text.value?.isBlank() == true || searchField.text.value?.isEmpty() == true) {
            updateList(result)
          } else {
            // Check if list elements need update.
            val updatedList = items.value?.toMutableList()?.map { item ->
              when (item) {
                is ListItemModel.HeroCard -> item.pokemon
              }
            } ?: emptyList()
            updateList(pokemons = updatedList)
          }
        }
      )
    }
  }
}