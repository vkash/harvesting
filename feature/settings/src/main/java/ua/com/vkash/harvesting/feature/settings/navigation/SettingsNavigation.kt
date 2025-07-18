package ua.com.vkash.harvesting.feature.settings.navigation

import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ua.com.vkash.harvesting.feature.settings.R
import ua.com.vkash.harvesting.feature.settings.SettingsRoute
import ua.com.vkash.harvesting.feature.settings.SettingsViewModel

@Serializable
data object SettingsRoute

fun NavController.navigateToSettings(navOptions: NavOptions? = null) =
    navigate(SettingsRoute, navOptions)

fun NavGraphBuilder.settingsScreen(
    onTitle: (String) -> Unit = {},
    onSubtitle: (String) -> Unit = {},
    onFieldClick: () -> Unit = {}
) {
    composable<SettingsRoute> { backStackEntry ->
        onTitle(stringResource(R.string.settings))
        onSubtitle("")

        val viewModel = hiltViewModel<SettingsViewModel>(backStackEntry)

        val fieldId = backStackEntry.savedStateHandle.get<Int>("fieldId")
        backStackEntry.savedStateHandle.remove<Int>("fieldId")

        if (fieldId != null) viewModel.selectField(fieldId)

        SettingsRoute(
            viewModel = viewModel,
            onFieldClick = onFieldClick
        )
    }
}
