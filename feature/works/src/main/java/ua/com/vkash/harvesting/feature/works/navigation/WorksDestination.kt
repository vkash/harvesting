package ua.com.vkash.harvesting.feature.works.navigation

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
import ua.com.vkash.harvesting.feature.works.R
import ua.com.vkash.harvesting.feature.works.WorksRoute

@Serializable
data object WorksRoute

fun NavController.navigateToWorks(navOptions: NavOptions? = null) =
    navigate(WorksRoute, navOptions)

fun NavDestination?.hasWorksRoute() = this@hasWorksRoute?.hasRoute<WorksRoute>() == true

fun NavGraphBuilder.worksScreen(
    onTitle: (String) -> Unit = {},
    onSubtitle: (String) -> Unit = {},
    onWorkSelect: (id: Int, date: Date) -> Unit
) {
    composable<WorksRoute> { backStackEntry ->
        onTitle(stringResource(R.string.completed_works))
        onSubtitle("")

        WorksRoute(
            viewModel = hiltViewModel(backStackEntry),
            onWorkSelect = onWorkSelect
        )
    }
}
