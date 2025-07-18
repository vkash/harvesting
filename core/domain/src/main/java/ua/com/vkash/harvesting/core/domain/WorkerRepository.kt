package ua.com.vkash.harvesting.core.domain

import com.vkash.harvesting.core.common.ApiResult
import ua.com.vkash.harvesting.core.model.data.Worker

interface WorkerRepository {
    suspend fun addWorkers(list: List<Worker>)

    suspend fun fetchWorkers(barcode: String): ApiResult<List<Worker>>

    suspend fun findWorker(barcode: String): Worker?
}
