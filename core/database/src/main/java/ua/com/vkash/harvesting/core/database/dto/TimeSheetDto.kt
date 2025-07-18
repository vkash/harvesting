package ua.com.vkash.harvesting.core.database.dto

import androidx.room.Embedded
import androidx.room.Relation
import ua.com.vkash.harvesting.core.database.model.FieldLocal
import ua.com.vkash.harvesting.core.database.model.TimeSheetLocal
import ua.com.vkash.harvesting.core.database.model.WorkerLocal

data class TimeSheetDto(
    @Embedded
    val timeSheet: TimeSheetLocal,
    @Relation(
        parentColumn = "worker_id",
        entityColumn = "rowid"
    )
    val worker: WorkerLocal?,
    @Relation(
        parentColumn = "field_id",
        entityColumn = "rowid"
    )
    val field: FieldLocal?
)
