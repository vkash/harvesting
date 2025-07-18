package ua.com.vkash.harvesting.core.data.repository

import ua.com.vkash.harvesting.core.common.ApiResult
import ua.com.vkash.harvesting.core.common.di.IoDispatcher
import ua.com.vkash.harvesting.core.common.map
import dagger.Lazy
import javax.inject.Inject
import kotlin.collections.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ua.com.vkash.harvesting.core.database.dao.FieldDao
import ua.com.vkash.harvesting.core.database.model.FieldLocal
import ua.com.vkash.harvesting.core.database.model.SquareLocal
import ua.com.vkash.harvesting.core.domain.FieldRepository
import ua.com.vkash.harvesting.core.model.data.Field
import ua.com.vkash.harvesting.core.model.data.FieldWithSquares
import ua.com.vkash.harvesting.core.model.data.Square
import ua.com.vkash.harvesting.core.network.ApiClient

class FieldRepositoryImpl @Inject constructor(
    private val apiClient: Lazy<ApiClient>,
    private val fieldDao: FieldDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : FieldRepository {
    override suspend fun addField(field: Field): Long = withContext(dispatcher) {
        fieldDao.insertField(
            FieldLocal(
                id = field.id,
                guid = field.guid,
                name = field.name
            )
        )
    }

    override suspend fun deleteFields() {
        withContext(dispatcher) {
            fieldDao.deleteFieldsAndSquares()
        }
    }

    override suspend fun fetchFields(barcode: String): ApiResult<List<FieldWithSquares>> =
        withContext(dispatcher) {
            apiClient.get().getFields(
                barcode
            ).map { list ->
                list.map { fieldRemote ->
                    FieldWithSquares(
                        guid = fieldRemote.guid,
                        name = fieldRemote.name,
                        squares = fieldRemote.squares.map { squareRemote ->
                            Square(
                                guid = squareRemote.guid,
                                name = squareRemote.name,
                                skuGuid = squareRemote.sku
                            )
                        }
                    )
                }
            }
        }

    private fun FieldLocal.toDomain() = Field(
        id = id,
        guid = guid,
        name = name
    )

    override fun getFields(): Flow<List<Field>> = fieldDao.getFields().map { list ->
        list.map { it.toDomain() }
    }.flowOn(dispatcher)

    override suspend fun getField(id: Int): Field? = withContext(dispatcher) {
        fieldDao.getField(id)?.toDomain()
    }

    private fun SquareLocal.toDomain() = Square(
        id = id,
        guid = guid,
        name = name,
        skuId = skuId,
        fieldId = fieldId
    )

    override fun getSquares(fieldId: Int): Flow<List<Square>> = fieldDao.getSquares(
        fieldId
    ).map { list ->
        list.map { it.toDomain() }
    }.flowOn(dispatcher)

    override suspend fun getSquare(id: Int): Square? = withContext(dispatcher) {
        fieldDao.getSquare(id)?.toDomain()
    }

    override suspend fun addSquares(list: List<Square>) {
        withContext(dispatcher) {
            fieldDao.insertSquares(
                list.map {
                    SquareLocal(
                        id = it.id,
                        guid = it.guid,
                        name = it.name,
                        fieldId = it.fieldId,
                        skuId = it.skuId
                    )
                }
            )
        }
    }
}
