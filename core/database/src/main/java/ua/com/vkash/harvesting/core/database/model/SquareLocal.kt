package ua.com.vkash.harvesting.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "squares",
    foreignKeys = [
        ForeignKey(
            entity = FieldLocal::class,
            parentColumns = ["rowid"],
            childColumns = ["field_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SquareLocal(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    val id: Int = 0,
    @ColumnInfo(name = "guid")
    val guid: String = "",
    @ColumnInfo(name = "name")
    val name: String = "",
    @ColumnInfo(name = "field_id", index = true)
    val fieldId: Int = 0,
    @ColumnInfo(name = "sku_id", index = true)
    val skuId: Int = 0
)
