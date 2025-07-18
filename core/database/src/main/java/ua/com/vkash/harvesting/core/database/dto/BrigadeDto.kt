package ua.com.vkash.harvesting.core.database.dto

import androidx.room.Embedded
import androidx.room.Relation
import ua.com.vkash.harvesting.core.database.model.BrigadeLocal
import ua.com.vkash.harvesting.core.database.model.WorkerLocal

data class BrigadeDto(
    @Embedded
    val brigade: BrigadeLocal = BrigadeLocal(),
    @Relation(
        parentColumn = "brigadier_id",
        entityColumn = "rowid"
    )
    val brigadier: WorkerLocal? = null,
    @Relation(
        parentColumn = "rowid",
        entityColumn = "brigade_id"
    )
    val items: List<BrigadeItemDetail> = emptyList()
)
