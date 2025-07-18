package ua.com.vkash.harvesting.feature.works.navigation

import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ua.com.vkash.harvesting.feature.works.R
import ua.com.vkash.harvesting.feature.works.WorkTypesRoute

@Serializable
data object WorkTypesRoute

fun NavController.navigateToWorkTypes(navOptions: NavOptions? = null) =
    navigate(WorkTypesRoute, navOptions)

fun NavGraphBuilder.workTypesScreen(
    onTitle: (String) -> Unit = {},
    onSubtitle: (String) -> Unit = {},
    onSelectWorkType: (id: Int) -> Unit
) {
    composable<WorkTypesRoute> { backStackEntry ->
        onTitle(stringResource(R.string.work_types))
        onSubtitle("")

        WorkTypesRoute(
            viewModel = hiltViewModel(backStackEntry),
            onSelectWorkType = onSelectWorkType
        )
    }
}
