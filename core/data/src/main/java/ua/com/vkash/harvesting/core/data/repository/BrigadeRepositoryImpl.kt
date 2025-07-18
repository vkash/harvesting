package ua.com.vkash.harvesting.core.data.repository

import ua.com.vkash.harvesting.core.common.ApiResult
import ua.com.vkash.harvesting.core.common.di.IoDispatcher
import dagger.Lazy
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ua.com.vkash.harvesting.core.database.dao.BrigadeDao
import ua.com.vkash.harvesting.core.database.dto.BrigadeDto
import ua.com.vkash.harvesting.core.database.model.BrigadeItemLocal
import ua.com.vkash.harvesting.core.database.model.BrigadeLocal
import ua.com.vkash.harvesting.core.database.model.EntityStateLocal.CREATED
import ua.com.vkash.harvesting.core.database.model.EntityStateLocal.SAVED
import ua.com.vkash.harvesting.core.database.model.EntityStateLocal.SENT
import ua.com.vkash.harvesting.core.domain.BrigadeRepository
import ua.com.vkash.harvesting.core.model.data.Brigade
import ua.com.vkash.harvesting.core.model.data.BrigadeDetail
import ua.com.vkash.harvesting.core.model.data.BrigadeItemDetail
import ua.com.vkash.harvesting.core.model.data.EntityState
import ua.com.vkash.harvesting.core.model.data.Worker
import ua.com.vkash.harvesting.core.network.ApiClient
import ua.com.vkash.harvesting.core.network.model.BrigadeRemote

class BrigadeRepositoryImpl @Inject constructor(
    private val apiClient: Lazy<ApiClient>,
    private val brigadeDao: BrigadeDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BrigadeRepository {
    override fun getBrigades(): Flow<List<Brigade>> = brigadeDao.getBrigades().map { list ->
        list.map { it.toDomain() }
    }.flowOn(dispatcher)

    override suspend fun addBrigade(): Long = withContext(dispatcher) {
        brigadeDao.insertBrigade(
            BrigadeLocal(guid = UUID.randomUUID().toString())
        )
    }

    override fun getBrigade(brigadeId: Int): Flow<BrigadeDetail> = brigadeDao.getBrigade(
        brigadeId
    ).map {
        it.toDomain()
    }.flowOn(dispatcher)

    private fun BrigadeLocal.toDomain() = Brigade(
        id = id,
        date = date,
        guid = guid,
        brigadierId = brigadierId,
        entityState = when (entityState) {
            CREATED -> EntityState.CREATED
            SAVED -> EntityState.SAVED
            SENT -> EntityState.SENT
        }
    )

    private fun BrigadeDto.toDomain() = BrigadeDetail(
        brigade = brigade.toDomain(),
        brigadier = brigadier?.let {
            Worker(
                id = it.id,
                guid = it.guid,
                name = it.name,
                barcode = it.barcode
            )
        },
        items = items.map {
            BrigadeItemDetail(
                id = it.id,
                brigadeId = it.brigadeId,
                workerId = it.workerId,
                workerName = it.workerName,
                workerBarcode = it.workerBarcode,
                workerGuid = it.workerGuid
            )
        }
    )

    override suspend fun setSaved(brigadeId: Int) {
        withContext(dispatcher) {
            brigadeDao.setState(SAVED, brigadeId)
        }
    }

    override suspend fun updateBrigadier(workerId: Int, brigadeId: Int) {
        withContext(dispatcher) {
            brigadeDao.updateBrigadier(workerId, brigadeId)
        }
    }

    override suspend fun addBrigadeWorker(workerId: Int, brigadeId: Int) {
        withContext(dispatcher) {
            val existWorker = brigadeDao.findBrigadeWorker(workerId, brigadeId)
            if (existWorker == null) {
                brigadeDao.insertWorker(
                    BrigadeItemLocal(
                        brigadeId = brigadeId,
                        workerId = workerId
                    )
                )
            }
        }
    }

    override suspend fun clearSent() {
        withContext(dispatcher) {
            brigadeDao.deleteBrigadeSent()
        }
    }

    override suspend fun getUnsent(): List<BrigadeDetail> = withContext(dispatcher) {
        brigadeDao.getBrigades(
            SAVED
        ).map {
            it.toDomain()
        }
    }

    override suspend fun setSent(brigadeId: Int) {
        withContext(dispatcher) {
            brigadeDao.setState(SENT, brigadeId)
        }
    }

    override suspend fun uploadBrigade(
        barcode: String,
        brigadeDetail: BrigadeDetail
    ): ApiResult<Unit> = withContext(dispatcher) {
        apiClient.get().postBrigade(
            barcode,
            BrigadeRemote(
                guid = brigadeDetail.brigade.guid,
                brigadier = brigadeDetail.brigadier?.guid.orEmpty(),
                workers = brigadeDetail.items.map { it.workerGuid }
            )
        )
    }
}
