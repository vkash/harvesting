package ua.com.vkash.harvesting.core.domain.usecase

import com.vkash.harvesting.core.common.ApiResult
import com.vkash.harvesting.core.common.di.IoDispatcher
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ua.com.vkash.harvesting.core.domain.BrigadeRepository
import ua.com.vkash.harvesting.core.domain.SettingsRepository

class UploadBrigadesUseCase @Inject constructor(
    private val brigadeRepository: BrigadeRepository,
    private val settingsRepository: SettingsRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): Boolean = withContext(dispatcher) {
        val userBarcode = settingsRepository.getUserBarcode()
        var succeeded = true
        val uploadData = brigadeRepository.getUnsent()
        uploadData.forEach {
            val response = brigadeRepository.uploadBrigade(userBarcode, it)

            if (response is ApiResult.Success) {
                brigadeRepository.setSent(it.brigade.id)
            } else {
                succeeded = false
            }
        }
        return@withContext succeeded
    }
}
