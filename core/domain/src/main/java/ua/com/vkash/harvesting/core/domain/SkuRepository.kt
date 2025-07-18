package ua.com.vkash.harvesting.core.domain

import ua.com.vkash.harvesting.core.common.ApiResult
import ua.com.vkash.harvesting.core.model.data.Sku

interface SkuRepository {
    suspend fun addSku(list: List<Sku>)

    suspend fun fetchSku(barcode: String): ApiResult<List<Sku>>

    suspend fun getTara(): List<Sku>

    suspend fun getSku(): List<Sku>

    suspend fun getSku(fieldId: Int): List<Sku>
}
