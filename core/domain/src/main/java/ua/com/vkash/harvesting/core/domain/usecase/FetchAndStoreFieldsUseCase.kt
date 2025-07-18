package ua.com.vkash.harvesting.core.domain.usecase

import com.vkash.harvesting.core.common.ApiResult
import com.vkash.harvesting.core.common.di.IoDispatcher
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ua.com.vkash.harvesting.core.domain.FieldRepository
import ua.com.vkash.harvesting.core.domain.SettingsRepository
import ua.com.vkash.harvesting.core.domain.SkuRepository
import ua.com.vkash.harvesting.core.model.data.Square

class FetchAndStoreFieldsUseCase @Inject constructor(
    private val skuRepository: SkuRepository,
    private val fieldRepository: FieldRepository,
    private val settingsRepository: SettingsRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): ApiResult<*> = withContext(dispatcher) {
        val userBarcode = settingsRepository.getUserBarcode()

        val result = fieldRepository.fetchFields(userBarcode)

        if (result !is ApiResult.Success) return@withContext result

        val skuMap = skuRepository.getSku().associate { it.guid to it.id }

        fieldRepository.deleteFields()

        val squares = result.data.flatMap { field ->
            val fieldId = fieldRepository.addField(field.toDomain())

            field.squares.map { square ->
                Square(
                    fieldId = fieldId.toInt(),
                    skuId = skuMap.getOrDefault(square.skuGuid, 0),
                    name = square.name,
                    guid = square.guid
                )
            }
        }

        fieldRepository.addSquares(squares)

        return@withContext result
    }
}
