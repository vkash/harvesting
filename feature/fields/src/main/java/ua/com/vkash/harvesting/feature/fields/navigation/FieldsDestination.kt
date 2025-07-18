package ua.com.vkash.harvesting.feature.fields.navigation

import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ua.com.vkash.harvesting.feature.fields.FieldsRoute
import ua.com.vkash.harvesting.feature.fields.R

@Serializable
data object FieldsRoute

fun NavController.navigateToFields(navOptions: NavOptions? = null) =
    navigate(FieldsRoute, navOptions)

fun NavGraphBuilder.fieldsScreen(
    onTitle: (String) -> Unit = {},
    onSubtitle: (String) -> Unit = {},
    onFieldSelect: (id: Int) -> Unit
) {
    composable<FieldsRoute> { backStackEntry ->
        onTitle(stringResource(R.string.fields))
        onSubtitle("")

        FieldsRoute(
            viewModel = hiltViewModel(backStackEntry),
            onFieldSelect = onFieldSelect
        )
    }
}
