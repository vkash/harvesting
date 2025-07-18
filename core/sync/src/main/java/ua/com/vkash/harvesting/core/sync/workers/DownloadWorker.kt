package ua.com.vkash.harvesting.core.sync.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.vkash.harvesting.core.common.ApiResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import ua.com.vkash.harvesting.core.domain.BrigadeRepository
import ua.com.vkash.harvesting.core.domain.HarvestRepository
import ua.com.vkash.harvesting.core.domain.SettingsRepository
import ua.com.vkash.harvesting.core.domain.TimeSheetRepository
import ua.com.vkash.harvesting.core.domain.WorkRepository
import ua.com.vkash.harvesting.core.domain.usecase.FetchAndStoreFieldsUseCase
import ua.com.vkash.harvesting.core.domain.usecase.FetchAndStoreSkuUseCase
import ua.com.vkash.harvesting.core.domain.usecase.FetchAndStoreWorkTypesUseCase
import ua.com.vkash.harvesting.core.domain.usecase.FetchAndStoreWorkersUseCase
import ua.com.vkash.harvesting.core.sync.KEY_MESSAGE
import ua.com.vkash.harvesting.core.sync.KEY_PROGRESS
import ua.com.vkash.harvesting.core.sync.R

@HiltWorker
class DownloadWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val fetchAndStoreSkuUseCase: FetchAndStoreSkuUseCase,
    private val fetchAndStoreFieldsUseCase: FetchAndStoreFieldsUseCase,
    private val fetchAndStoreWorkersUseCase: FetchAndStoreWorkersUseCase,
    private val fetchAndStoreWorkTypesUseCase: FetchAndStoreWorkTypesUseCase,
    private val brigadeRepository: BrigadeRepository,
    private val harvestRepository: HarvestRepository,
    private val workRepository: WorkRepository,
    private val timeSheetRepository: TimeSheetRepository,
    private val settingsRepository: SettingsRepository
) : CoroutineWorker(appContext, workerParams) {

    private val syncTitles = appContext.resources.getStringArray(R.array.sync_titles)

    override suspend fun doWork(): Result {
        updateProgress(0)

        brigadeRepository.clearSent()
        harvestRepository.clearSent()
        workRepository.clearSent()
        timeSheetRepository.clearSent()
        settingsRepository.setDefaults()

        updateProgress(1)

        val result1 = fetchAndStoreSkuUseCase()
        if (result1 !is ApiResult.Success) {
            return Result.failure(result1.asData())
        }

        updateProgress(2)

        val result2 = fetchAndStoreFieldsUseCase()
        if (result2 !is ApiResult.Success) {
            return Result.failure(result2.asData())
        }

        updateProgress(3)

        val result3 = fetchAndStoreWorkersUseCase()
        if (result3 !is ApiResult.Success) {
            return Result.failure(result3.asData())
        }

        updateProgress(4)

        val result4 = fetchAndStoreWorkTypesUseCase()
        if (result4 !is ApiResult.Success) {
            return Result.failure(result4.asData())
        }

        updateProgress(5)

        return Result.success()
    }

    private fun <T> ApiResult<T>.asData(): Data {
        val message = when (this) {
            is ApiResult.Success -> syncTitles.last()
            is ApiResult.ServerError -> error.message ?: "Server error"
            is ApiResult.NetworkError -> error.message ?: "Network error"
            is ApiResult.Unauthorised -> applicationContext.getString(R.string.auth_error)
            is ApiResult.UnknownError -> applicationContext.getString(R.string.unknown_error)
            is ApiResult.OutdatedApp -> applicationContext.getString(R.string.outdated_app)
        }

        return Data.Builder().putString(KEY_MESSAGE, message).build()
    }

    private suspend fun updateProgress(step: Int) {
        val message = syncTitles.getOrNull(step)
        val progress = step / syncTitles.size.toFloat()
        setProgress(
            Data.Builder()
                .putFloat(KEY_PROGRESS, progress)
                .putString(KEY_MESSAGE, message)
                .build()
        )
    }

    companion object Companion {
        const val WORK_NAME = "Sync-WorkerLocal"
    }
}
