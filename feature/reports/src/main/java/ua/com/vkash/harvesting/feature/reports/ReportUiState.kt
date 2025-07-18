package ua.com.vkash.harvesting.feature.reports

sealed interface ReportUiState {
    data object Loading : ReportUiState
    data class Success(val data: String = "") : ReportUiState
}