package ua.com.vkash.harvesting.core.domain.usecase

import com.vkash.harvesting.core.common.ApiResult
import com.vkash.harvesting.core.common.di.IoDispatcher
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ua.com.vkash.harvesting.core.domain.SettingsRepository
import ua.com.vkash.harvesting.core.domain.TimeSheetRepository

class UploadTimeSheetsUseCase @Inject constructor(
    private val timeSheetRepository: TimeSheetRepository,
    private val settingsRepository: SettingsRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): Boolean = withContext(dispatcher) {
        val userBarcode = settingsRepository.getUserBarcode()

        val data = timeSheetRepository.getUnsent()
        if (data.isEmpty()) return@withContext true

        var succeeded = true
        val response = timeSheetRepository.uploadTimeSheets(userBarcode, data)

        if (response is ApiResult.Success) {
            timeSheetRepository.setSent(data.map { it.id })
        } else {
            succeeded = false
        }
        return@withContext succeeded
    }
}
