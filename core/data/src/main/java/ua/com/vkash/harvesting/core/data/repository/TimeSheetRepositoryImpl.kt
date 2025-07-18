package ua.com.vkash.harvesting.core.data.repository

import ua.com.vkash.harvesting.core.common.ApiResult
import ua.com.vkash.harvesting.core.common.di.IoDispatcher
import dagger.Lazy
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ua.com.vkash.harvesting.core.database.dao.TimeSheetDao
import ua.com.vkash.harvesting.core.database.dto.TimeSheetDto
import ua.com.vkash.harvesting.core.database.model.EntityStateLocal
import ua.com.vkash.harvesting.core.database.model.TimeSheetLocal
import ua.com.vkash.harvesting.core.domain.TimeSheetRepository
import ua.com.vkash.harvesting.core.model.data.TimeSheetDetail
import ua.com.vkash.harvesting.core.network.ApiClient
import ua.com.vkash.harvesting.core.network.model.TimeSheetRemote

class TimeSheetRepositoryImpl @Inject constructor(
    private val apiClient: Lazy<ApiClient>,
    private val timeSheetDao: TimeSheetDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : TimeSheetRepository {
    override suspend fun clearSent() {
        withContext(dispatcher) {
            timeSheetDao.deleteSent()
        }
    }

    override fun getTimeSheets(): Flow<List<TimeSheetDetail>> = timeSheetDao.getAll().map { list ->
        list.map { it.toDomain() }
    }.flowOn(dispatcher)

    override suspend fun addTimeSheet(workerId: Int, fieldId: Int) {
        withContext(dispatcher) {
            val last = timeSheetDao.getLast(workerId)
            timeSheetDao.insert(
                TimeSheetLocal(
                    workerId = workerId,
                    fieldId = fieldId,
                    eventStart = last?.eventStart?.not() != false,
                    entityState = EntityStateLocal.SAVED
                )
            )
        }
    }

    private fun TimeSheetDto.toDomain() = TimeSheetDetail(
        id = timeSheet.id,
        workerId = timeSheet.workerId,
        workerGuid = worker?.guid.orEmpty(),
        fieldId = timeSheet.fieldId,
        fieldGuid = field?.guid.orEmpty(),
        eventTime = timeSheet.eventTime,
        eventStart = timeSheet.eventStart,
        workerName = worker?.name.orEmpty(),
        fieldName = field?.name.orEmpty()
    )

    override suspend fun getUnsent(): List<TimeSheetDetail> = withContext(dispatcher) {
        timeSheetDao.getUnsent(
            EntityStateLocal.SAVED
        ).map {
            it.toDomain()
        }
    }

    override suspend fun uploadTimeSheets(
        barcode: String,
        list: List<TimeSheetDetail>
    ): ApiResult<Unit> = withContext(dispatcher) {
        apiClient.get().postTimeSheets(
            barcode,
            list.map {
                TimeSheetRemote(
                    worker = it.workerGuid,
                    field = it.fieldGuid,
                    time = it.eventTime
                )
            }
        )
    }

    override suspend fun setSent(timeSheetId: List<Int>) {
        withContext(dispatcher) {
            timeSheetDao.setState(EntityStateLocal.SENT, timeSheetId)
        }
    }
}
