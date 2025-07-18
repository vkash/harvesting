package ua.com.vkash.harvesting.feature.reports.navigation

import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import ua.com.vkash.harvesting.core.model.data.ReportId
import ua.com.vkash.harvesting.feature.reports.R
import ua.com.vkash.harvesting.feature.reports.ReportViewModel
import ua.com.vkash.harvesting.feature.reports.ReportsRoute

@Serializable
data class ReportRoute(val id: String)

fun NavController.navigateToReport(id: String, navOptions: NavOptions? = null) =
    navigate(ReportRoute(id), navOptions)

fun NavGraphBuilder.reportScreen(
    onTitle: (String) -> Unit = {},
    onSubtitle: (String) -> Unit = {}
) {
    composable<ReportRoute> { backStackEntry ->
        val route = backStackEntry.toRoute<ReportRoute>()
        onTitle(
            when (route.id) {
                ReportId.HarvestToday.id -> stringResource(R.string.harvest_today)
                ReportId.HarvestByBrigade.id -> stringResource(R.string.report_by_brigade)
                else -> stringResource(R.string.unknown_error)
            }
        )
        onSubtitle(stringResource(R.string.scan_badge))

        ReportsRoute(
            viewModel = hiltViewModel<ReportViewModel>(backStackEntry)
        )
    }
}
