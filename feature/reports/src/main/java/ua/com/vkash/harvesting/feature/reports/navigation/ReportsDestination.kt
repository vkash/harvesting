package ua.com.vkash.harvesting.feature.reports.navigation

import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ua.com.vkash.harvesting.feature.reports.R
import ua.com.vkash.harvesting.feature.reports.ReportsRoute

@Serializable
data object ReportsRoute

fun NavController.navigateToReports(navOptions: NavOptions? = null) =
    navigate(ReportsRoute, navOptions)

fun NavGraphBuilder.reportsScreen(
    onTitle: (String) -> Unit = {},
    onSubtitle: (String) -> Unit = {},
    onReportSelected: (id: String) -> Unit
) {
    composable<ReportsRoute> { backStackEntry ->
        onTitle(stringResource(R.string.reports))
        onSubtitle("")

        ReportsRoute(
            viewModel = hiltViewModel(backStackEntry),
            onReportClick = onReportSelected
        )
    }
}
