package ua.com.vkash.harvesting.feature.brigade

import ua.com.vkash.harvesting.core.model.data.BrigadeDetail

sealed class BrigadeUiState(val isReadOnly: Boolean) {
    data object Loading : BrigadeUiState(true)
    data class Success(
        val data: BrigadeDetail = BrigadeDetail(),
        val finish: Boolean = false
    ) : BrigadeUiState(data.isReadOnly) {
        fun allowSave() = data.check() && !data.isReadOnly
    }
}
