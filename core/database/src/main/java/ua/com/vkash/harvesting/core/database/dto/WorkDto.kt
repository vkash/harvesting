package ua.com.vkash.harvesting.core.database.dto

import androidx.room.Embedded
import androidx.room.Relation
import ua.com.vkash.harvesting.core.database.model.FieldLocal
import ua.com.vkash.harvesting.core.database.model.SquareLocal
import ua.com.vkash.harvesting.core.database.model.WorkLocal
import ua.com.vkash.harvesting.core.database.model.WorkTypeLocal
import ua.com.vkash.harvesting.core.database.model.WorkerLocal

data class WorkDto(
    @Embedded
    val work: WorkLocal = WorkLocal(),
    @Relation(
        parentColumn = "brigadier_id",
        entityColumn = "rowid"
    )
    val brigadier: WorkerLocal? = null,
    @Relation(
        parentColumn = "work_type_id",
        entityColumn = "rowid"
    )
    val workType: WorkTypeLocal? = null,
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
