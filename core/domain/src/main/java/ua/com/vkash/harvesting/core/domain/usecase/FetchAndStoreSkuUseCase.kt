package ua.com.vkash.harvesting.core.domain.usecase

import ua.com.vkash.harvesting.core.common.ApiResult
import ua.com.vkash.harvesting.core.common.di.IoDispatcher
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ua.com.vkash.harvesting.core.domain.SettingsRepository
import ua.com.vkash.harvesting.core.domain.SkuRepository

class FetchAndStoreSkuUseCase @Inject constructor(
    private val skuRepository: SkuRepository,
    private val settingsRepository: SettingsRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): ApiResult<*> = withContext(dispatcher) {
        val userBarcode = settingsRepository.getUserBarcode()

        val result = skuRepository.fetchSku(userBarcode)

        if (result !is ApiResult.Success) return@withContext result

        skuRepository.addSku(result.data)

        return@withContext result
    }
}
