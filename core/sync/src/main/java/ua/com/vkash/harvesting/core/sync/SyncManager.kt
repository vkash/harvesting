package ua.com.vkash.harvesting.core.sync

import kotlinx.coroutines.flow.Flow

interface SyncManager {
    val syncInfo: Flow<SyncInfo>
    fun requestSync()
}
