package ua.com.vkash.harvesting.feature.harvest.navigation

import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import java.util.Date
import kotlinx.serialization.Serializable
import ua.com.vkash.harvesting.feature.harvest.HarvestRoute
import ua.com.vkash.harvesting.feature.harvest.HarvestViewModel
import ua.com.vkash.harvesting.feature.harvest.R

@Serializable
data class HarvestRoute(val id: Int, val time: Long)

fun NavController.navigateToHarvest(id: Int, date: Date = Date(), navOptions: NavOptions? = null) =
    navigate(HarvestRoute(id, date.time), navOptions)

fun NavGraphBuilder.harvestScreen(
    onTitle: (String) -> Unit = {},
    onSubtitle: (String) -> Unit = {},
    onFinish: () -> Unit = {},
    onSquareSelect: (fieldId: Int) -> Unit = { },
    onTaraSelect: () -> Unit = { },
    onWeightSelect: (Double) -> Unit = { },
    onQtySelect: (Int) -> Unit = { }
) {
    composable<HarvestRoute> { backStackEntry ->
        val viewModel = hiltViewModel<HarvestViewModel>(backStackEntry)

        onTitle(stringResource(R.string.harvest_today))

        val route = backStackEntry.toRoute<HarvestRoute>()
        onSubtitle(
            stringResource(
                R.string.num_date,
                route.id,
                route.time
            )
        )

        val squareId = backStackEntry.savedStateHandle.get<Int>("squareId")
        backStackEntry.savedStateHandle.remove<Int>("squareId")
        if (squareId != null) viewModel.updateSquare(squareId)

        val skuId = backStackEntry.savedStateHandle.get<Int>("skuId")
        backStackEntry.savedStateHandle.remove<Int>("skuId")
        if (skuId != null) viewModel.updateTara(skuId)

        val weight = backStackEntry.savedStateHandle.get<Double>("weight")
        backStackEntry.savedStateHandle.remove<Double>("weight")
        if (weight != null) viewModel.updateWeight(weight)

        val qty = backStackEntry.savedStateHandle.get<Double>("qty")
        backStackEntry.savedStateHandle.remove<Double>("qty")
        if (qty != null) viewModel.updateTaraQty(qty)

        HarvestRoute(
            viewModel = viewModel,
            onFinish = onFinish,
            onSquareSelect = onSquareSelect,
            onTaraSelect = onTaraSelect,
            onWeightSelect = onWeightSelect,
            onQtySelect = onQtySelect
        )
    }
}
