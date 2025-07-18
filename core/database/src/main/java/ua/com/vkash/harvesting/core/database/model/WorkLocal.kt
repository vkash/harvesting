package ua.com.vkash.harvesting.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "works")
data class WorkLocal(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    val id: Int = 0,
    @ColumnInfo(name = "date")
    val date: Date = Date(),
    @ColumnInfo("guid")
    val guid: String = "",
    @ColumnInfo(name = "brigadier_id")
    val brigadierId: Int = 0,
    @ColumnInfo(name = "field_id")
    val fieldId: Int = 0,
    @ColumnInfo(name = "square_id")
    val squareId: Int = 0,
    @ColumnInfo(name = "work_type_id")
    val typeTypeId: Int = 0,
    @ColumnInfo(name = "qty")
    val qty: Double = 0.0,
    @ColumnInfo(name = "state")
    val entityState: EntityStateLocal = EntityStateLocal.CREATED
)
