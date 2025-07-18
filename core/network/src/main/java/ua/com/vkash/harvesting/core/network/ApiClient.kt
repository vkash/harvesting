package ua.com.vkash.harvesting.core.network

import ua.com.vkash.harvesting.core.common.ApiResult
import java.io.File
import ua.com.vkash.harvesting.core.network.model.BrigadeRemote
import ua.com.vkash.harvesting.core.network.model.FieldRemote
import ua.com.vkash.harvesting.core.network.model.HarvestRemote
import ua.com.vkash.harvesting.core.network.model.SkuRemote
import ua.com.vkash.harvesting.core.network.model.TimeSheetRemote
import ua.com.vkash.harvesting.core.network.model.UserRemote
import ua.com.vkash.harvesting.core.network.model.WorkRemote
import ua.com.vkash.harvesting.core.network.model.WorkTypeRemote
import ua.com.vkash.harvesting.core.network.model.WorkerRemote

interface ApiClient {
    suspend fun getUser(barcode: String): ApiResult<UserRemote>
    suspend fun getSku(barcode: String): ApiResult<List<SkuRemote>>
    suspend fun getFields(barcode: String): ApiResult<List<FieldRemote>>
    suspend fun getWorkers(barcode: String): ApiResult<List<WorkerRemote>>
    suspend fun getWorkTypes(barcode: String): ApiResult<List<WorkTypeRemote>>
    suspend fun postBrigade(barcode: String, brigadeRemote: BrigadeRemote): ApiResult<Unit>
    suspend fun postWork(barcode: String, workRemote: WorkRemote): ApiResult<Unit>
    suspend fun postHarvest(barcode: String, harvestRemote: HarvestRemote): ApiResult<Unit>
    suspend fun postTimeSheets(barcode: String, data: List<TimeSheetRemote>): ApiResult<Unit>
    suspend fun getReport(barcode: String, id: String, workerBarcode: String): ApiResult<String>
    suspend fun getUpdate(listener: suspend (bytes: Long, total: Long?) -> Unit): ApiResult<File>
    suspend fun checkUpdate(): Boolean
}
