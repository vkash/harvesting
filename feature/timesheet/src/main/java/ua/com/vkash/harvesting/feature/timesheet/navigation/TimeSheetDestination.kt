package ua.com.vkash.harvesting.feature.timesheet.navigation

import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ua.com.vkash.harvesting.feature.timesheet.R
import ua.com.vkash.harvesting.feature.timesheet.TimeSheetRoute

@Serializable
data object TimeSheetRoute

fun NavController.navigateToTimeSheet(navOptions: NavOptions? = null) =
    navigate(TimeSheetRoute, navOptions)

fun NavGraphBuilder.timeSheetScreen(
    onTitle: (String) -> Unit = {},
    onSubtitle: (String) -> Unit = {}
) {
    composable<TimeSheetRoute> { backStackEntry ->
        onTitle(stringResource(R.string.time_sheet))
        onSubtitle(stringResource(R.string.scan_barcode))

        TimeSheetRoute(
            viewModel = hiltViewModel(backStackEntry)
        )
    }
}
