package ua.com.vkash.harvesting.navigation

import androidx.navigation.NavController

fun NavController.navigateUpWithArgs(vararg keyValues: Pair<String, Any?>) {
    keyValues.forEach { (k, v) ->
        previousBackStackEntry?.savedStateHandle?.set(k, v)
    }
    navigateUp()
}
