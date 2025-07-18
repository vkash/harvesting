package ua.com.vkash.harvesting.core.domain

import ua.com.vkash.harvesting.core.common.ApiResult
import kotlinx.coroutines.flow.Flow
import ua.com.vkash.harvesting.core.model.data.TimeSheetDetail

interface TimeSheetRepository {
    suspend fun clearSent()

    fun getTimeSheets(): Flow<List<TimeSheetDetail>>

    suspend fun addTimeSheet(workerId: Int, fieldId: Int)

    suspend fun getUnsent(): List<TimeSheetDetail>

    suspend fun uploadTimeSheets(barcode: String, list: List<TimeSheetDetail>): ApiResult<Unit>

    suspend fun setSent(timeSheetId: List<Int>)
}
