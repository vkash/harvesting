package ua.com.vkash.harvesting.feature.sku.navigation

import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ua.com.vkash.harvesting.feature.sku.R
import ua.com.vkash.harvesting.feature.sku.SkusRoute

@Serializable
data class SkusRoute(val type: Int = SKU)

const val SKU = 0
const val TARA = 1

fun NavController.navigateToSkus(type: Int = SKU, navOptions: NavOptions? = null) =
    navigate(SkusRoute(type), navOptions)

fun NavGraphBuilder.skusScreen(
    onTitle: (String) -> Unit = {},
    onSubtitle: (String) -> Unit = {},
    onSkuSelect: (id: Int) -> Unit
) {
    composable<SkusRoute> { backStackEntry ->
        onTitle(stringResource(R.string.sku))
        onSubtitle("")

        SkusRoute(
            viewModel = hiltViewModel(backStackEntry),
            onSkuSelect = onSkuSelect
        )
    }
}
