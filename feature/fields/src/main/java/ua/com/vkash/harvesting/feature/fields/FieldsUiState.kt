package ua.com.vkash.harvesting.feature.fields

import ua.com.vkash.harvesting.core.model.data.Field

sealed interface FieldsUiState {
    data object Loading : FieldsUiState
    data class Success(val data: List<Field> = emptyList()) : FieldsUiState {
        fun isEmpty() = data.isEmpty()
    }
}
