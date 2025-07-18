package ua.com.vkash.harvesting.core.domain

import com.vkash.harvesting.core.common.ApiResult
import kotlinx.coroutines.flow.Flow
import ua.com.vkash.harvesting.core.model.data.Work
import ua.com.vkash.harvesting.core.model.data.WorkDetail
import ua.com.vkash.harvesting.core.model.data.WorkType

interface WorkRepository {
    suspend fun addWorkTypes(list: List<WorkType>)

    suspend fun fetchWorkTypes(barcode: String): ApiResult<List<WorkType>>

    fun getWorkTypes(): Flow<List<WorkType>>

    fun getWorks(): Flow<List<Work>>

    suspend fun addWork(fieldId: Int, squareId: Int): Long

    fun getWork(id: Int): Flow<WorkDetail>

    suspend fun setSaved(workId: Int)

    suspend fun updateBrigadier(workerId: Int, workId: Int)

    suspend fun updateWorkQty(qty: Double, workId: Int)

    suspend fun updateSquare(squareId: Int, workId: Int)

    suspend fun updateWorkType(workTypeId: Int, workId: Int)

    suspend fun clearSent()

    suspend fun getUnsent(): List<WorkDetail>

    suspend fun uploadWork(barcode: String, workDetail: WorkDetail): ApiResult<Unit>

    suspend fun setSent(workId: Int)
}
