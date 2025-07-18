package ua.com.vkash.harvesting.feature.harvest

import ua.com.vkash.harvesting.core.model.data.Harvest

sealed interface HarvestsUiState {
    data object Loading : HarvestsUiState
    data class Success(val data: List<Harvest> = emptyList()) : HarvestsUiState {
        fun isEmpty() = data.isEmpty()
    }
}
