package ua.com.vkash.harvesting.feature.works

import ua.com.vkash.harvesting.core.model.data.Work

sealed interface WorksUiState {
    data object Loading : WorksUiState
    data class Success(val data: List<Work> = emptyList()) : WorksUiState {
        fun isEmpty() = data.isEmpty()
    }
}
