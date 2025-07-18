package ua.com.vkash.harvesting.core.domain.usecase

import ua.com.vkash.harvesting.core.common.ApiResult
import ua.com.vkash.harvesting.core.common.di.IoDispatcher
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ua.com.vkash.harvesting.core.domain.HarvestRepository
import ua.com.vkash.harvesting.core.domain.SettingsRepository

class UploadHarvestsUseCase @Inject constructor(
    private val harvestRepository: HarvestRepository,
    private val settingsRepository: SettingsRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): Boolean = withContext(dispatcher) {
        val userBarcode = settingsRepository.getUserBarcode()
        var succeeded = true
        val uploadData = harvestRepository.getUnsent()
        uploadData.forEach {
            val response = harvestRepository.uploadHarvest(userBarcode, it)

            if (response is ApiResult.Success) {
                harvestRepository.setSent(it.harvest.id)
            } else {
                succeeded = false
            }
        }
        return@withContext succeeded
    }
}
