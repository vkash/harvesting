package ua.com.vkash.harvesting.feature.harvest.navigation

import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import java.util.Date
import kotlinx.serialization.Serializable
import ua.com.vkash.harvesting.feature.harvest.R
import ua.com.vkash.harvesting.feature.harvest.HarvestsRoute

@Serializable
data object HarvestsRoute

fun NavController.navigateToHarvests(navOptions: NavOptions? = null) =
    navigate(HarvestsRoute, navOptions)

fun NavDestination?.hasHarvestsRoute() = this@hasHarvestsRoute?.hasRoute<HarvestsRoute>() == true

fun NavGraphBuilder.harvestsScreen(
    onTitle: (String) -> Unit = {},
    onSubtitle: (String) -> Unit = {},
    onHarvestSelect: (id: Int, date: Date) -> Unit
) {
    composable<HarvestsRoute> { backStackEntry ->
        onTitle(stringResource(R.string.harvesting))
        onSubtitle("")

        HarvestsRoute(
            viewModel = hiltViewModel(backStackEntry),
            onHarvestSelect = onHarvestSelect
        )
    }
}
