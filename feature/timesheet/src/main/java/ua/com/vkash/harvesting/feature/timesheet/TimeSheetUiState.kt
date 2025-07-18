package ua.com.vkash.harvesting.feature.timesheet

import ua.com.vkash.harvesting.core.model.data.TimeSheetDetail

sealed interface TimeSheetUiState {
    data object Loading : TimeSheetUiState
    data class Success(val data: List<TimeSheetDetail> = emptyList()) : TimeSheetUiState {
        fun isEmpty() = data.isEmpty()
    }
}
