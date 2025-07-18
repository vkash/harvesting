package ua.com.vkash.harvesting.feature.settings

sealed interface SyncUiState {
    data object Done : SyncUiState
    data object Ready : SyncUiState
    data class Process(
        val progress: Float = 0f,
        val message: String = ""
    ) : SyncUiState

    data class Error(val message: String = "") : SyncUiState
}
