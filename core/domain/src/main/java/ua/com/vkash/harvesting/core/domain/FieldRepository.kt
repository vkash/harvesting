package ua.com.vkash.harvesting.core.domain

import com.vkash.harvesting.core.common.ApiResult
import kotlinx.coroutines.flow.Flow
import ua.com.vkash.harvesting.core.model.data.Field
import ua.com.vkash.harvesting.core.model.data.FieldWithSquares
import ua.com.vkash.harvesting.core.model.data.Square

interface FieldRepository {
    suspend fun addField(field: Field): Long

    suspend fun deleteFields()

    suspend fun fetchFields(barcode: String): ApiResult<List<FieldWithSquares>>

    fun getFields(): Flow<List<Field>>

    suspend fun getField(id: Int): Field?

    fun getSquares(fieldId: Int): Flow<List<Square>>

    suspend fun getSquare(id: Int): Square?

    suspend fun addSquares(list: List<Square>)
}
