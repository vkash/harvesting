package ua.com.vkash.harvesting.core.domain.usecase

import com.vkash.harvesting.core.common.ApiResult
import com.vkash.harvesting.core.common.di.IoDispatcher
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ua.com.vkash.harvesting.core.domain.SettingsRepository
import ua.com.vkash.harvesting.core.domain.WorkerRepository

class FetchAndStoreWorkersUseCase @Inject constructor(
    private val workerRepository: WorkerRepository,
    private val settingsRepository: SettingsRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): ApiResult<*> = withContext(dispatcher) {
        val userBarcode = settingsRepository.getUserBarcode()

        val result = workerRepository.fetchWorkers(userBarcode)

        if (result !is ApiResult.Success) return@withContext result

        workerRepository.addWorkers(result.data)

        return@withContext result
    }
}
