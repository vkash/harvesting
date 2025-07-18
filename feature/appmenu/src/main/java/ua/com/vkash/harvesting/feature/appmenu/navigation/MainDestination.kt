package ua.com.vkash.harvesting.feature.appmenu.navigation

import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ua.com.vkash.harvesting.feature.appmenu.MainCard
import ua.com.vkash.harvesting.feature.appmenu.MainRoute
import ua.com.vkash.harvesting.feature.appmenu.MainScreenViewModel
import ua.com.vkash.harvesting.feature.appmenu.R

@Serializable
data object MainRoute

fun NavDestination?.hasMainRoute() = this@hasMainRoute?.hasRoute<MainRoute>() == true

fun NavGraphBuilder.mainScreen(
    onTitle: (String) -> Unit = {},
    onSubtitle: (String) -> Unit = {},
    onCardSelected: (MainCard) -> Unit
) {
    composable<MainRoute> { backStackEntry ->
        val viewModel = hiltViewModel<MainScreenViewModel>(backStackEntry)
        val userName by viewModel.userName.collectAsStateWithLifecycle(null)
        userName?.let {
            // skip init value
            onTitle(it.ifBlank { stringResource(R.string.scan_badge) })
        }
        onSubtitle(stringResource(R.string.agro_farm))

        MainRoute(
            viewModel = viewModel,
            onCardClick = onCardSelected
        )
    }
}
