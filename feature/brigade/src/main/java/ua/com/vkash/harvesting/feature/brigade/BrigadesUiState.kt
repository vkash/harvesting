package ua.com.vkash.harvesting.feature.brigade

import ua.com.vkash.harvesting.core.model.data.Brigade

sealed interface BrigadesUiState {
    data object Loading : BrigadesUiState
    data class Success(val data: List<Brigade> = emptyList()) : BrigadesUiState {
        fun isEmpty() = data.isEmpty()
    }
}
