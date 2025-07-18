package ua.com.vkash.harvesting.core.model.data

import java.util.Collections.emptyList

data class FieldWithSquares(
    val guid: String = "",
    val name: String = "",
    val squares: List<Square> = emptyList()
) {
    fun toDomain() = Field(
        guid = guid,
        name = name
    )
}
