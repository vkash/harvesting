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
import ua.com.vkash.harvesting.core.database.dao.HarvestDao
import ua.com.vkash.harvesting.core.database.dto.HarvestDto
import ua.com.vkash.harvesting.core.database.model.EntityStateLocal.CREATED
import ua.com.vkash.harvesting.core.database.model.EntityStateLocal.SAVED
import ua.com.vkash.harvesting.core.database.model.EntityStateLocal.SENT
import ua.com.vkash.harvesting.core.database.model.HarvestLocal
import ua.com.vkash.harvesting.core.domain.HarvestRepository
import ua.com.vkash.harvesting.core.model.data.EntityState
import ua.com.vkash.harvesting.core.model.data.Field
import ua.com.vkash.harvesting.core.model.data.Harvest
import ua.com.vkash.harvesting.core.model.data.HarvestDetail
import ua.com.vkash.harvesting.core.model.data.Sku
import ua.com.vkash.harvesting.core.model.data.Square
import ua.com.vkash.harvesting.core.model.data.Worker
import ua.com.vkash.harvesting.core.network.ApiClient
import ua.com.vkash.harvesting.core.network.model.HarvestRemote

class HarvestRepositoryImpl @Inject constructor(
    private val apiClient: Lazy<ApiClient>,
    private val harvestDao: HarvestDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : HarvestRepository {
    override fun getHarvests(): Flow<List<Harvest>> = harvestDao.getHarvests().map { list ->
        list.map { it.toDomain() }
    }.flowOn(dispatcher)

    private fun HarvestLocal.toDomain() = Harvest(
        id = id,
        date = date,
        guid = guid,
        workerId = workerId,
        skuId = skuId,
        taraId = taraId,
        fieldId = fieldId,
        squareId = squareId,
        weight = weight,
        taraQty = taraQty,
        entityState = when (entityState) {
            CREATED -> EntityState.CREATED
            SAVED -> EntityState.SAVED
            SENT -> EntityState.SENT
        }
    )

    private fun HarvestDto.toDomain() = HarvestDetail(
        harvest = harvest.toDomain(),
        worker = worker?.let {
            Worker(
                id = it.id,
                guid = it.guid,
                name = it.name,
                barcode = it.barcode
            )
        },
        sku = sku?.let {
            Sku(
                id = it.id,
                guid = it.guid,
                name = it.name,
                type = it.type
            )
        },
        tara = tara?.let {
            Sku(
                id = it.id,
                guid = it.guid,
                name = it.name,
                type = it.type
            )
        },
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
                skuId = it.skuId,
                fieldId = it.fieldId
            )
        }
    )

    override suspend fun addHarvest(
        skuId: Int,
        taraId: Int,
        fieldId: Int,
        squareId: Int
    ): Long = withContext(dispatcher) {
        harvestDao.insertHarvest(
            HarvestLocal(
                guid = UUID.randomUUID().toString(),
                skuId = skuId,
                taraId = taraId,
                fieldId = fieldId,
                squareId = squareId
            )
        )
    }

    override fun getHarvest(id: Int): Flow<HarvestDetail> = harvestDao.getHarvest(id).map {
        it.toDomain()
    }.flowOn(dispatcher)

    override suspend fun getUnsent(): List<HarvestDetail> = withContext(dispatcher) {
        harvestDao.getHarvests(
            SAVED
        ).map {
            it.toDomain()
        }
    }

    override suspend fun setSaved(harvestId: Int) {
        withContext(dispatcher) {
            harvestDao.setState(SAVED, harvestId)
        }
    }

    override suspend fun setSent(harvestId: Int) {
        withContext(dispatcher) {
            harvestDao.setState(SENT, harvestId)
        }
    }

    override suspend fun updateWorker(workerId: Int, harvestId: Int) {
        withContext(dispatcher) {
            harvestDao.updateWorker(workerId, harvestId)
        }
    }

    override suspend fun updateTara(skuId: Int, harvestId: Int) {
        withContext(dispatcher) {
            harvestDao.updateTara(skuId, harvestId)
        }
    }

    override suspend fun updateWeight(weight: Double, harvestId: Int) {
        withContext(dispatcher) {
            harvestDao.updateWeight(weight, harvestId)
        }
    }

    override suspend fun updateTaraQty(qty: Int, harvestId: Int) {
        withContext(dispatcher) {
            harvestDao.updateTaraQty(qty, harvestId)
        }
    }

    override suspend fun updateSquare(
        squareId: Int,
        skuId: Int,
        harvestId: Int
    ) {
        withContext(dispatcher) {
            harvestDao.updateSquareSku(squareId, skuId, harvestId)
        }
    }

    override suspend fun clearSent() {
        withContext(dispatcher) {
            harvestDao.deleteSent()
        }
    }

    override suspend fun uploadHarvest(
        barcode: String,
        harvestDetail: HarvestDetail
    ): ApiResult<Unit> = withContext(dispatcher) {
        apiClient.get().postHarvest(
            barcode,
            HarvestRemote(
                guid = harvestDetail.harvest.guid,
                worker = harvestDetail.worker?.guid.orEmpty(),
                sku = harvestDetail.sku?.guid.orEmpty(),
                tara = harvestDetail.tara?.guid.orEmpty(),
                field = harvestDetail.field?.guid.orEmpty(),
                square = harvestDetail.square?.guid.orEmpty(),
                weight = harvestDetail.harvest.weight,
                taraQty = harvestDetail.harvest.taraQty
            )
        )
    }
}
