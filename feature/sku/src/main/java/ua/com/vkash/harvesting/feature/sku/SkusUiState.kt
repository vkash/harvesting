package ua.com.vkash.harvesting.feature.sku

import ua.com.vkash.harvesting.core.model.data.Sku

sealed interface SkusUiState {
    data object Loading : SkusUiState
    data class Success(val data: List<Sku> = emptyList()) : SkusUiState {
        fun isEmpty() = data.isEmpty()
    }
}
