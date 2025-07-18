package ua.com.vkash.harvesting.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "time_sheets")
data class TimeSheetLocal(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    val id: Int = 0,
    @ColumnInfo(name = "worker_id", index = true)
    val workerId: Int = 0,
    @ColumnInfo(name = "field_id", index = true)
    val fieldId: Int = 0,
    @ColumnInfo(name = "event_time")
    val eventTime: Date = Date(),
    @ColumnInfo(name = "event_start")
    val eventStart: Boolean = false,
    @ColumnInfo(name = "state")
    val entityState: EntityStateLocal = EntityStateLocal.CREATED
)
