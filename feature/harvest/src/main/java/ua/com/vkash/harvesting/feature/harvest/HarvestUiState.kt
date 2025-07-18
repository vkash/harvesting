package ua.com.vkash.harvesting.feature.harvest

import ua.com.vkash.harvesting.core.model.data.HarvestDetail

sealed class HarvestUiState(val isReadOnly: Boolean) {
    data object Loading : HarvestUiState(true)
    data class Success(
        val data: HarvestDetail = HarvestDetail(),
        val finish: Boolean = false
    ) : HarvestUiState(data.isReadOnly) {
        fun allowSave() = data.check() && !data.isReadOnly
    }
}
