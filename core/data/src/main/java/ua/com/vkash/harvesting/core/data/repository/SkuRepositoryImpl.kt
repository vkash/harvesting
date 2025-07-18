package ua.com.vkash.harvesting.core.data.repository

import com.vkash.harvesting.core.common.di.IoDispatcher
import com.vkash.harvesting.core.common.map
import dagger.Lazy
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ua.com.vkash.harvesting.core.database.dao.SkuDao
import ua.com.vkash.harvesting.core.database.model.SkuLocal
import ua.com.vkash.harvesting.core.domain.SkuRepository
import ua.com.vkash.harvesting.core.model.data.Sku
import ua.com.vkash.harvesting.core.network.ApiClient

class SkuRepositoryImpl @Inject constructor(
    private val apiClient: Lazy<ApiClient>,
    private val skuDao: SkuDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : SkuRepository {
    override suspend fun addSku(list: List<Sku>) {
        withContext(dispatcher) {
            val skuList = list.map {
                SkuLocal(
                    id = it.id,
                    guid = it.guid,
                    name = it.name,
                    type = it.type
                )
            }
            skuDao.insertAll(skuList)
        }
    }

    override suspend fun fetchSku(barcode: String) = withContext(dispatcher) {
        apiClient.get().getSku(barcode).map { list ->
            list.map {
                Sku(
                    guid = it.guid,
                    name = it.name,
                    type = it.skuType
                )
            }
        }
    }

    private fun SkuLocal.toDomain() = Sku(
        id = id,
        guid = guid,
        name = name,
        type = type
    )

    override suspend fun getTara() = withContext(dispatcher) {
        skuDao.getTara().map { it.toDomain() }
    }

    override suspend fun getSku() = withContext(dispatcher) {
        skuDao.getSku().map { it.toDomain() }
    }

    override suspend fun getSku(fieldId: Int) = withContext(dispatcher) {
        skuDao.getSku(fieldId).map { it.toDomain() }
    }
}
