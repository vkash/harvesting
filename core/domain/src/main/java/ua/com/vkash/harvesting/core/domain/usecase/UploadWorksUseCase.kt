package ua.com.vkash.harvesting.core.domain.usecase

import com.vkash.harvesting.core.common.ApiResult
import com.vkash.harvesting.core.common.di.IoDispatcher
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ua.com.vkash.harvesting.core.domain.SettingsRepository
import ua.com.vkash.harvesting.core.domain.WorkRepository

class UploadWorksUseCase @Inject constructor(
    private val workRepository: WorkRepository,
    private val settingsRepository: SettingsRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): Boolean = withContext(dispatcher) {
        val userBarcode = settingsRepository.getUserBarcode()
        var succeeded = true
        val uploadData = workRepository.getUnsent()
        uploadData.forEach {
            val response = workRepository.uploadWork(userBarcode, it)

            if (response is ApiResult.Success) {
                workRepository.setSent(it.work.id)
            } else {
                succeeded = false
            }
        }
        return@withContext succeeded
    }
}
