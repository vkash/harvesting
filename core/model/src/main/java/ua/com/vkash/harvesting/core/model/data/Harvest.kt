package ua.com.vkash.harvesting.core.model.data

import java.util.Date

data class Harvest(
    val id: Int = 0,
    val date: Date = Date(),
    val guid: String = "",
    val workerId: Int = 0,
    val skuId: Int = 0,
    val taraId: Int = 0,
    val fieldId: Int = 0,
    val squareId: Int = 0,
    val weight: Double = 0.0,
    val taraQty: Int = 0,
    val entityState: EntityState = EntityState.CREATED
)
