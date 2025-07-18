package ua.com.vkash.harvesting.feature.brigade.navigation

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
import ua.com.vkash.harvesting.feature.brigade.BrigadesRoute
import ua.com.vkash.harvesting.feature.brigade.R

@Serializable
data object BrigadesRoute

fun NavController.navigateToBrigades(navOptions: NavOptions? = null) =
    navigate(BrigadesRoute, navOptions)

fun NavDestination?.hasBrigadesRoute() = this@hasBrigadesRoute?.hasRoute<BrigadesRoute>() == true

fun NavGraphBuilder.brigadesScreen(
    onTitle: (String) -> Unit = {},
    onSubtitle: (String) -> Unit = {},
    onBrigadeSelect: (id: Int, date: Date) -> Unit
) {
    composable<BrigadesRoute> { backStackEntry ->
        onTitle(stringResource(R.string.brigades))
        onSubtitle("")

        BrigadesRoute(
            viewModel = hiltViewModel(backStackEntry),
            onBrigadeSelect = onBrigadeSelect
        )
    }
}
