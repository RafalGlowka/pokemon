package com.glowka.rafal.pokemon.presentation.flow.dashboard

import com.glowka.rafal.pokemon.domain.utils.EmptyParam
import com.glowka.rafal.pokemon.domain.utils.logD
import com.glowka.rafal.pokemon.presentation.architecture.BaseFlow
import com.glowka.rafal.pokemon.presentation.architecture.Screen
import com.glowka.rafal.pokemon.presentation.architecture.getViewModelToFlow
import com.glowka.rafal.pokemon.presentation.flow.dashboard.details.DetailsViewModelToFlowInterface
import com.glowka.rafal.pokemon.presentation.flow.dashboard.list.ListViewModelToFlowInterface
import com.glowka.rafal.pokemon.presentation.utils.exhaustive

/**
 * Created by Rafal on 16.04.2021.
 */
sealed class DashboardResult {
  object Terminated : DashboardResult()
}

class DashboardFlowImpl :
  BaseFlow<EmptyParam, DashboardResult>(flowScopeName = DashboardFlow.SCOPE_NAME), DashboardFlow {

  override fun onStart(param: EmptyParam): Screen<*, *, *> {
    return switchScreen(
      screen = DashboardFlow.Screens.List,
      param = EmptyParam.EMPTY
    ) { event ->
      when (event) {
        is ListViewModelToFlowInterface.Event.ShowDetails -> showDetails(event)
        ListViewModelToFlowInterface.Event.Back -> finish(result = DashboardResult.Terminated)
      }.exhaustive
    }
  }

  private fun showDetails(showDetails: ListViewModelToFlowInterface.Event.ShowDetails) {
    logD("show Details")
    switchScreen(
      screen = DashboardFlow.Screens.Details,
      param = DetailsViewModelToFlowInterface.Param(
        pokemon = showDetails.pokemon,
        isFavourite = showDetails.isFavourite
      ),
    ) { event ->
      when (event) {
        DetailsViewModelToFlowInterface.Event.Back -> goBackToList()
      }.exhaustive
    }
  }

  private fun goBackToList() {
    val viewModel = getViewModelToFlow(DashboardFlow.Screens.List)
    viewModel.refreshFavourites()
    switchBackTo(DashboardFlow.Screens.List)
  }

}