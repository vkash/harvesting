package ua.com.vkash.harvesting.core.data.repository

import com.vkash.harvesting.core.common.ApiResult
import com.vkash.harvesting.core.common.di.IoDispatcher
import com.vkash.harvesting.core.common.map
import dagger.Lazy
import java.util.UUID
import javax.inject.Inject
import kotlin.collections.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ua.com.vkash.harvesting.core.database.dao.WorkDao
import ua.com.vkash.harvesting.core.database.dto.WorkDto
import ua.com.vkash.harvesting.core.database.model.EntityStateLocal.CREATED
import ua.com.vkash.harvesting.core.database.model.EntityStateLocal.SAVED
import ua.com.vkash.harvesting.core.database.model.EntityStateLocal.SENT
import ua.com.vkash.harvesting.core.database.model.WorkLocal
import ua.com.vkash.harvesting.core.database.model.WorkTypeLocal
import ua.com.vkash.harvesting.core.domain.WorkRepository
import ua.com.vkash.harvesting.core.model.data.EntityState
import ua.com.vkash.harvesting.core.model.data.Field
import ua.com.vkash.harvesting.core.model.data.Square
import ua.com.vkash.harvesting.core.model.data.Work
import ua.com.vkash.harvesting.core.model.data.WorkDetail
import ua.com.vkash.harvesting.core.model.data.WorkType
import ua.com.vkash.harvesting.core.model.data.Worker
import ua.com.vkash.harvesting.core.network.ApiClient
import ua.com.vkash.harvesting.core.network.model.WorkRemote

class WorkRepositoryImpl @Inject constructor(
    private val apiClient: Lazy<ApiClient>,
    private val workDao: WorkDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : WorkRepository {
    override suspend fun addWorkTypes(list: List<WorkType>) {
        withContext(dispatcher) {
            val workTypes = list.map {
                WorkTypeLocal(
                    id = it.id,
                    name = it.name,
                    guid = it.guid
                )
            }
            workDao.insertAll(workTypes)
        }
    }

    override suspend fun fetchWorkTypes(barcode: String): ApiResult<List<WorkType>> =
        withContext(dispatcher) {
            apiClient.get().getWorkTypes(
                barcode
            ).map { list ->
                list.map {
                    WorkType(
                        guid = it.guid,
                        name = it.name
                    )
                }
            }
        }

    private fun WorkTypeLocal.toDomain() = WorkType(
        id = id,
        name = name,
        guid = guid
    )

    override fun getWorkTypes(): Flow<List<WorkType>> = workDao.getWorkTypes().map { list ->
        list.map { it.toDomain() }
    }.flowOn(dispatcher)

    private fun WorkLocal.toDomain() = Work(
        id = id,
        date = date,
        guid = guid,
        brigadierId = brigadierId,
        fieldId = fieldId,
        squareId = squareId,
        typeTypeId = typeTypeId,
        qty = qty,
        entityState = when (entityState) {
            CREATED -> EntityState.CREATED
            SAVED -> EntityState.SAVED
            SENT -> EntityState.SENT
        }
    )

    override fun getWorks(): Flow<List<Work>> = workDao.getWorks().map { list ->
        list.map { it.toDomain() }
    }.flowOn(dispatcher)

    override suspend fun addWork(fieldId: Int, squareId: Int): Long = withContext(dispatcher) {
        workDao.insertWork(
            WorkLocal(
                guid = UUID.randomUUID().toString(),
                fieldId = fieldId,
                squareId = squareId
            )
        )
    }

    override fun getWork(id: Int): Flow<WorkDetail> = workDao.getWork(id).map { it.toDomain() }

    override suspend fun setSaved(workId: Int) {
        withContext(dispatcher) {
            workDao.setState(SAVED, workId)
        }
    }

    override suspend fun updateBrigadier(workerId: Int, workId: Int) {
        withContext(dispatcher) {
            workDao.updateBrigadier(workerId, workId)
        }
    }

    override suspend fun updateWorkQty(qty: Double, workId: Int) {
        withContext(dispatcher) {
            workDao.updateWorkQty(qty, workId)
        }
    }

    override suspend fun updateSquare(squareId: Int, workId: Int) {
        withContext(dispatcher) {
            workDao.updateSquare(squareId, workId)
        }
    }

    override suspend fun updateWorkType(workTypeId: Int, workId: Int) {
        withContext(dispatcher) {
            workDao.updateWorkType(workTypeId, workId)
        }
    }

    override suspend fun clearSent() {
        withContext(dispatcher) {
            workDao.deleteSentWorks()
        }
    }

    private fun WorkDto.toDomain() = WorkDetail(
        work = work.toDomain(),
        brigadier = brigadier?.let {
            Worker(
                id = it.id,
                guid = it.guid,
                name = it.name,
                barcode = it.barcode
            )
        },
        workType = workType?.toDomain(),
        field = field?.let {
            Field(
                id = it.id,
                guid = it.guid,
                name = it.name
            )
        },
        square = square?.let {
            Square(
                id = it.id,
                guid = it.guid,
                name = it.name,
                fieldId = it.fieldId,
                skuId = it.skuId
            )
        }
    )

    override suspend fun getUnsent(): List<WorkDetail> = withContext(dispatcher) {
        workDao.getWork(
            SAVED
        ).map {
            it.toDomain()
        }
    }

    override suspend fun uploadWork(
        barcode: String,
        workDetail: WorkDetail
    ): ApiResult<Unit> = withContext(dispatcher) {
        apiClient.get().postWork(
            barcode,
            WorkRemote(
                guid = workDetail.work.guid,
                brigadier = workDetail.brigadier?.guid.orEmpty(),
                field = workDetail.field?.guid.orEmpty(),
                square = workDetail.square?.guid.orEmpty(),
                workType = workDetail.workType?.guid.orEmpty(),
                qty = workDetail.work.qty
            )
        )
    }

    override suspend fun setSent(workId: Int) {
        withContext(dispatcher) {
            workDao.setState(SENT, workId)
        }
    }
}
