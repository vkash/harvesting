package ua.com.vkash.harvesting.core.model.data

import java.util.Date

data class Work(
    val id: Int = 0,
    val date: Date = Date(),
    val guid: String = "",
    val brigadierId: Int = 0,
    val fieldId: Int = 0,
    val squareId: Int = 0,
    val typeTypeId: Int = 0,
    val qty: Double = 0.0,
    val entityState: EntityState = EntityState.CREATED
)
