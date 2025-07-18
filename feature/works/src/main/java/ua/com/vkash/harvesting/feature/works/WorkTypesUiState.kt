package ua.com.vkash.harvesting.feature.works

import ua.com.vkash.harvesting.core.model.data.WorkType

sealed interface WorkTypesUiState {
    data object Loading : WorkTypesUiState
    data class Success(val data: List<WorkType> = emptyList()) : WorkTypesUiState {
        fun isEmpty() = data.isEmpty()
    }
}
