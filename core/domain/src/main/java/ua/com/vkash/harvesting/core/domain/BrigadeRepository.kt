package ua.com.vkash.harvesting.core.domain

import com.vkash.harvesting.core.common.ApiResult
import kotlinx.coroutines.flow.Flow
import ua.com.vkash.harvesting.core.model.data.Brigade
import ua.com.vkash.harvesting.core.model.data.BrigadeDetail

interface BrigadeRepository {
    fun getBrigades(): Flow<List<Brigade>>

    suspend fun addBrigade(): Long

    fun getBrigade(brigadeId: Int): Flow<BrigadeDetail>

    suspend fun setSaved(brigadeId: Int)

    suspend fun updateBrigadier(workerId: Int, brigadeId: Int)

    suspend fun addBrigadeWorker(workerId: Int, brigadeId: Int)

    suspend fun clearSent()

    suspend fun getUnsent(): List<BrigadeDetail>

    suspend fun setSent(brigadeId: Int)

    suspend fun uploadBrigade(barcode: String, brigadeDetail: BrigadeDetail): ApiResult<Unit>
}
