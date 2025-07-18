package ua.com.vkash.harvesting.feature.brigade.navigation

import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import java.util.Date
import kotlinx.serialization.Serializable
import ua.com.vkash.harvesting.feature.brigade.BrigadeRoute
import ua.com.vkash.harvesting.feature.brigade.BrigadeViewModel
import ua.com.vkash.harvesting.feature.brigade.R

@Serializable
data class BrigadeRoute(val id: Int, val time: Long)

fun NavController.navigateToBrigade(id: Int, date: Date = Date(), navOptions: NavOptions? = null) =
    navigate(BrigadeRoute(id, date.time), navOptions)

fun NavGraphBuilder.brigadeScreen(
    onTitle: (String) -> Unit = {},
    onSubtitle: (String) -> Unit = {},
    onFinish: () -> Unit = {}
) {
    composable<BrigadeRoute> { backStackEntry ->
        onTitle(stringResource(R.string.brigade))

        val route = backStackEntry.toRoute<BrigadeRoute>()
        onSubtitle(
            stringResource(
                R.string.num_date,
                route.id,
                route.time
            )
        )

        BrigadeRoute(
            viewModel = hiltViewModel<BrigadeViewModel>(backStackEntry),
            onFinish = onFinish
        )
    }
}
