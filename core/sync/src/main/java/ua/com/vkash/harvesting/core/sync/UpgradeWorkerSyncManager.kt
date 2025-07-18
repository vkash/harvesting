package ua.com.vkash.harvesting.core.sync

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import ua.com.vkash.harvesting.core.sync.workers.UpgradeWorker

class UpgradeWorkerSyncManager @Inject constructor(
    @ApplicationContext private val context: Context
) : SyncManager {
    override val syncInfo: Flow<SyncInfo> = WorkManager.getInstance(context)
        .getWorkInfosForUniqueWorkFlow(UpgradeWorker.WORK_NAME)
        .mapToSyncInfo()
        .conflate()

    override fun requestSync() {
        val request = OneTimeWorkRequestBuilder<DelegatingWorker>()
            .setInputData(UpgradeWorker::class.delegatedData())
            .build()
        WorkManager.getInstance(context).enqueueUniqueWork(
            UpgradeWorker.WORK_NAME,
            ExistingWorkPolicy.KEEP,
            request
        )
    }
}
