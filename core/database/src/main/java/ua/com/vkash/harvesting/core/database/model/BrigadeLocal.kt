package ua.com.vkash.harvesting.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "brigades")
data class BrigadeLocal(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    val id: Int = 0,
    @ColumnInfo(name = "date")
    val date: Date = Date(),
    @ColumnInfo(name = "guid")
    val guid: String = "",
    @ColumnInfo(name = "brigadier_id")
    val brigadierId: Int = 0,
    @ColumnInfo(name = "state")
    val entityState: EntityStateLocal = EntityStateLocal.CREATED
)
