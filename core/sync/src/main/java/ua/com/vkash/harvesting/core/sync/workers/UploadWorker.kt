package ua.com.vkash.harvesting.core.sync.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import ua.com.vkash.harvesting.core.domain.usecase.UploadBrigadesUseCase
import ua.com.vkash.harvesting.core.domain.usecase.UploadHarvestsUseCase
import ua.com.vkash.harvesting.core.domain.usecase.UploadTimeSheetsUseCase
import ua.com.vkash.harvesting.core.domain.usecase.UploadWorksUseCase

@HiltWorker
class UploadWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val uploadBrigadesUseCase: UploadBrigadesUseCase,
    private val uploadHarvestsUseCase: UploadHarvestsUseCase,
    private val uploadWorksUseCase: UploadWorksUseCase,
    private val uploadTimeSheetsUseCase: UploadTimeSheetsUseCase
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        val result = uploadHarvestsUseCase()
            .and(uploadBrigadesUseCase())
            .and(uploadWorksUseCase())
            .and(uploadTimeSheetsUseCase())

        return if (result) Result.success() else Result.retry()
    }

    companion object {
        const val WORK_NAME = "Upload-WorkerLocal"
    }
}
