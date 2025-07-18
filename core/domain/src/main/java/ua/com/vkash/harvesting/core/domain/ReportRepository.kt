package ua.com.vkash.harvesting.core.domain

import com.vkash.harvesting.core.common.ApiResult
import kotlinx.coroutines.flow.Flow
import ua.com.vkash.harvesting.core.model.data.Report

interface ReportRepository {
    fun getReports(): Flow<List<Report>>

    suspend fun fetchReport(barcode: String, id: String, workerBarcode: String): ApiResult<String>
}
