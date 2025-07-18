package ua.com.vkash.harvesting.feature.works.navigation

import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import java.util.Date
import kotlinx.serialization.Serializable
import ua.com.vkash.harvesting.feature.works.R
import ua.com.vkash.harvesting.feature.works.WorkRoute
import ua.com.vkash.harvesting.feature.works.WorkViewModel

@Serializable
data class WorkRoute(val id: Int, val time: Long)

fun NavController.navigateToWork(id: Int, date: Date = Date(), navOptions: NavOptions? = null) =
    navigate(WorkRoute(id, date.time), navOptions)

fun NavGraphBuilder.workScreen(
    onTitle: (String) -> Unit = {},
    onSubtitle: (String) -> Unit = {},
    onWorkTypeSelect: () -> Unit = { },
    onSquareSelect: (fieldId: Int) -> Unit = { },
    onQtySelect: (Double) -> Unit = { },
    onFinish: () -> Unit = {}
) {
    composable<WorkRoute> { backStackEntry ->
        val viewModel = hiltViewModel<WorkViewModel>(backStackEntry)

        onTitle(stringResource(R.string.completed_works))

        val route = backStackEntry.toRoute<WorkRoute>()
        onSubtitle(
            stringResource(R.string.num_date, route.id, route.time)
        )

        val workTypeId = backStackEntry.savedStateHandle.get<Int>("workTypeId")
        backStackEntry.savedStateHandle.remove<Int>("workTypeId")
        if (workTypeId != null) viewModel.updateWorkType(workTypeId)

        val squareId = backStackEntry.savedStateHandle.get<Int>("squareId")
        backStackEntry.savedStateHandle.remove<Int>("squareId")
        if (squareId != null) viewModel.updateSquare(squareId)

        val inputValue = backStackEntry.savedStateHandle.get<Double>("inputValue")
        backStackEntry.savedStateHandle.remove<Double>("inputValue")
        if (inputValue != null) viewModel.updateWorkQty(inputValue)

        WorkRoute(
            viewModel = viewModel,
            onWorkTypeSelect = onWorkTypeSelect,
            onSquareSelect = onSquareSelect,
            onQtySelect = onQtySelect,
            onFinish = onFinish
        )
    }
}
