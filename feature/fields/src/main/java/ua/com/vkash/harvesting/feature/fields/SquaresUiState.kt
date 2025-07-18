package ua.com.vkash.harvesting.feature.fields

import ua.com.vkash.harvesting.core.model.data.Square

sealed interface SquaresUiState {
    data object Loading : SquaresUiState
    data class Success(val data: List<Square> = emptyList()) : SquaresUiState {
        fun isEmpty() = data.isEmpty()
    }
}
