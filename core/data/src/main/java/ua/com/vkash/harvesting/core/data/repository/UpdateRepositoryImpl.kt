package ua.com.vkash.harvesting.core.data.repository

import ua.com.vkash.harvesting.core.common.ApiResult
import ua.com.vkash.harvesting.core.common.di.IoDispatcher
import ua.com.vkash.harvesting.core.common.map
import dagger.Lazy
import java.io.File
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ua.com.vkash.harvesting.core.domain.UpdateListener
import ua.com.vkash.harvesting.core.domain.UpdateRepository
import ua.com.vkash.harvesting.core.network.ApiClient

class UpdateRepositoryImpl @Inject constructor(
    private val apiClient: Lazy<ApiClient>,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : UpdateRepository {
    override suspend fun fetchUpdate(
        listener: UpdateListener
    ): ApiResult<File> = withContext(dispatcher) {
        apiClient.get().getUpdate(listener::onProgress).map { it }
    }

    override suspend fun checkUpdate(): Boolean = withContext(dispatcher) {
        apiClient.get().checkUpdate()
    }
}
