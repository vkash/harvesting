package ua.com.vkash.harvesting.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "harvests")
data class HarvestLocal(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    val id: Int = 0,
    @ColumnInfo(name = "date")
    val date: Date = Date(),
    @ColumnInfo("guid")
    val guid: String = "",
    @ColumnInfo(name = "worker_id")
    val workerId: Int = 0,
    @ColumnInfo(name = "sku_id")
    val skuId: Int = 0,
    @ColumnInfo(name = "tara_id")
    val taraId: Int = 0,
    @ColumnInfo(name = "field_id")
    val fieldId: Int = 0,
    @ColumnInfo(name = "square_id")
    val squareId: Int = 0,
    @ColumnInfo(name = "weight")
    val weight: Double = 0.0,
    @ColumnInfo(name = "tara_qty")
    val taraQty: Int = 0,
    @ColumnInfo(name = "state")
    val entityState: EntityStateLocal = EntityStateLocal.CREATED
)
