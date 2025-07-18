package ua.com.vkash.harvesting.core.domain

import ua.com.vkash.harvesting.core.common.ApiResult
import kotlinx.coroutines.flow.Flow
import ua.com.vkash.harvesting.core.model.data.Harvest
import ua.com.vkash.harvesting.core.model.data.HarvestDetail

interface HarvestRepository {
    fun getHarvests(): Flow<List<Harvest>>

    suspend fun addHarvest(
        skuId: Int,
        taraId: Int,
        fieldId: Int,
        squareId: Int
    ): Long

    fun getHarvest(id: Int): Flow<HarvestDetail>

    suspend fun getUnsent(): List<HarvestDetail>

    suspend fun setSaved(harvestId: Int)

    suspend fun setSent(harvestId: Int)

    suspend fun updateWorker(workerId: Int, harvestId: Int)

    suspend fun updateTara(skuId: Int, harvestId: Int)

    suspend fun updateWeight(weight: Double, harvestId: Int)

    suspend fun updateTaraQty(qty: Int, harvestId: Int)

    suspend fun updateSquare(squareId: Int, skuId: Int, harvestId: Int)

    suspend fun clearSent()

    suspend fun uploadHarvest(barcode: String, harvestDetail: HarvestDetail): ApiResult<Unit>
}
