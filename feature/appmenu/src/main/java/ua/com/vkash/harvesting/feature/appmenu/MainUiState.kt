package ua.com.vkash.harvesting.feature.appmenu

sealed interface MainUiState {
    data object Loading : MainUiState
    data class Success(val data: List<MainCard> = emptyList()) : MainUiState {
        fun isEmpty() = data.isEmpty()
    }
}
