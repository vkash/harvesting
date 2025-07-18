package ua.com.vkash.harvesting.feature.works

import ua.com.vkash.harvesting.core.model.data.WorkDetail

sealed class WorkUiState(val isReadOnly: Boolean) {
    data object Loading : WorkUiState(true)
    data class Success(
        val data: WorkDetail = WorkDetail(),
        val finish: Boolean = false
    ) : WorkUiState(data.isReadOnly) {
        fun allowSave() = data.check() && !data.isReadOnly
    }
}