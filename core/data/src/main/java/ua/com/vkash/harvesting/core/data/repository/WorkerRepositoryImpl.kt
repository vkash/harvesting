package ua.com.vkash.harvesting.core.data.repository

import ua.com.vkash.harvesting.core.common.ApiResult
import ua.com.vkash.harvesting.core.common.di.IoDispatcher
import ua.com.vkash.harvesting.core.common.map
import dagger.Lazy
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ua.com.vkash.harvesting.core.database.dao.WorkerDao
import ua.com.vkash.harvesting.core.database.model.WorkerLocal
import ua.com.vkash.harvesting.core.domain.WorkerRepository
import ua.com.vkash.harvesting.core.model.data.Worker
import ua.com.vkash.harvesting.core.network.ApiClient

class WorkerRepositoryImpl @Inject constructor(
    private val apiClient: Lazy<ApiClient>,
    private val workerDao: WorkerDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : WorkerRepository {
    override suspend fun addWorkers(list: List<Worker>) {
        withContext(dispatcher) {
            val workerList = list.map {
                WorkerLocal(
                    id = it.id,
                    guid = it.guid,
                    name = it.name,
                    barcode = it.barcode
                )
            }
            workerDao.insertAll(workerList)
        }
    }

    private fun WorkerLocal.toDomain() = Worker(
        id = id,
        guid = guid,
        name = name,
        barcode = barcode
    )

    override suspend fun fetchWorkers(barcode: String): ApiResult<List<Worker>> = withContext(
        dispatcher
    ) {
        apiClient.get().getWorkers(
            barcode
        ).map { list ->
            list.map {
                Worker(
                    guid = it.guid,
                    name = it.name,
                    barcode = barcode
                )
            }
        }
    }

    override suspend fun findWorker(barcode: String): Worker? = withContext(dispatcher) {
        workerDao.findWorker(
            "%$barcode%"
        )?.toDomain()
    }
}
