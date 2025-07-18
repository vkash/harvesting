package ua.com.vkash.harvesting.core.sync

import androidx.work.WorkInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

data class SyncInfo(
    val state: SyncState,
    val progress: Float,
    val message: String
)

enum class SyncState {
    IDLE,
    SYNCING,
    ERROR,
    DONE
}

const val KEY_PROGRESS = "key_progress_value"
const val KEY_MESSAGE = "key_message_value"

fun Flow<List<WorkInfo>>.mapToSyncInfo(): Flow<SyncInfo> = transform {
    it.forEach { workInfo ->
        emit(
            SyncInfo(
                state = when (workInfo.state) {
                    WorkInfo.State.ENQUEUED, WorkInfo.State.BLOCKED -> SyncState.IDLE
                    WorkInfo.State.RUNNING -> SyncState.SYNCING
                    WorkInfo.State.SUCCEEDED -> SyncState.DONE
                    WorkInfo.State.FAILED, WorkInfo.State.CANCELLED -> SyncState.ERROR
                },
                progress = workInfo.progress.getFloat(KEY_PROGRESS, 0f).coerceIn(0f, 1f),
                message = if (workInfo.state == WorkInfo.State.FAILED) {
                    workInfo.outputData.getString(KEY_MESSAGE).orEmpty()
                } else {
                    workInfo.progress.getString(KEY_MESSAGE).orEmpty()
                }
            )
        )
    }
}
