package ua.com.vkash.harvesting.core.database.dto

import androidx.room.Embedded
import androidx.room.Relation
import ua.com.vkash.harvesting.core.database.model.FieldLocal
import ua.com.vkash.harvesting.core.database.model.HarvestLocal
import ua.com.vkash.harvesting.core.database.model.SkuLocal
import ua.com.vkash.harvesting.core.database.model.SquareLocal
import ua.com.vkash.harvesting.core.database.model.WorkerLocal

data class HarvestDto(
    @Embedded
    val harvest: HarvestLocal = HarvestLocal(),
    @Relation(
        parentColumn = "worker_id",
        entityColumn = "rowid"
    )
    val worker: WorkerLocal? = null,
    @Relation(
        parentColumn = "sku_id",
        entityColumn = "rowid"
    )
    val sku: SkuLocal? = null,
    @Relation(
        parentColumn = "tara_id",
        entityColumn = "rowid"
    )
    val tara: SkuLocal? = null,
    @Relation(
        parentColumn = "field_id",
        entityColumn = "rowid"
    )
    val field: FieldLocal? = null,
    @Relation(
        parentColumn = "square_id",
        entityColumn = "rowid"
    )
    val square: SquareLocal? = null
)
