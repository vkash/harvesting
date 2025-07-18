package ua.com.priberry.harvesting.feature.calculator.navigation

import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ua.com.priberry.harvesting.feature.calculator.CalculatorRoute
import ua.com.priberry.harvesting.feature.calculator.R

@Serializable
data class CalculatorRoute(
    val inputKey: String,
    val inputValue: Double,
    val isFractional: Boolean
)

fun NavController.navigateToCalculator(
    inputKey: String = "inputValue",
    inputValue: Double = 0.0,
    isFractional: Boolean = true,
    navOptions: NavOptions? = null
) =
    navigate(CalculatorRoute(inputKey, inputValue, isFractional), navOptions)

fun NavGraphBuilder.calculatorScreen(
    onTitle: (String) -> Unit = {},
    onSubtitle: (String) -> Unit = {},
    onConfirmInput: (String, Double) -> Unit
) {
    composable<CalculatorRoute> { backStackEntry ->
        onTitle(stringResource(R.string.input_value))
        onSubtitle("")

        CalculatorRoute(
            viewModel = hiltViewModel(backStackEntry),
            onConfirmInput = onConfirmInput
        )
    }
}
