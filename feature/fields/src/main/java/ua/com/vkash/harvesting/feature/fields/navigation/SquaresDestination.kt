package ua.com.vkash.harvesting.feature.fields.navigation

import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ua.com.vkash.harvesting.feature.fields.R
import ua.com.vkash.harvesting.feature.fields.SquaresRoute

@Serializable
data class SquaresRoute(val fieldId: Int)

fun NavController.navigateToSquares(fieldId: Int, navOptions: NavOptions? = null) =
    navigate(SquaresRoute(fieldId), navOptions)

fun NavGraphBuilder.squaresScreen(
    onTitle: (String) -> Unit = {},
    onSubtitle: (String) -> Unit = {},
    onSelectSquare: (id: Int) -> Unit
) {
    composable<SquaresRoute> { backStackEntry ->
        onTitle(stringResource(R.string.squares))
        onSubtitle("")

        SquaresRoute(
            viewModel = hiltViewModel(backStackEntry),
            onSelectSquare = onSelectSquare
        )
    }
}
