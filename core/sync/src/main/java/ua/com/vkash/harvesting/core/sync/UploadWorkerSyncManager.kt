package ua.com.vkash.harvesting.core.sync

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import ua.com.vkash.harvesting.core.sync.workers.UploadWorker

class UploadWorkerSyncManager @Inject constructor(
    @ApplicationContext private val context: Context
) : SyncManager {
    override val syncInfo: Flow<SyncInfo> = WorkManager.getInstance(context)
        .getWorkInfosForUniqueWorkFlow(UploadWorker.WORK_NAME)
        .mapToSyncInfo()
        .conflate()

    override fun requestSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val request = OneTimeWorkRequestBuilder<DelegatingWorker>()
            .setInitialDelay(7L, TimeUnit.SECONDS)
            .setConstraints(constraints)
            .setInputData(UploadWorker::class.delegatedData())
            .build()
        WorkManager.getInstance(context).enqueueUniqueWork(
            UploadWorker.WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }
}
