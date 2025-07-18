package ua.com.vkash.harvesting.feature.reports

import ua.com.vkash.harvesting.core.model.data.Report

sealed interface ReportsUiState {
    data object Loading : ReportsUiState
    data class Success(val data: List<Report> = emptyList()) : ReportsUiState {
        fun isEmpty() = data.isEmpty()
    }
}