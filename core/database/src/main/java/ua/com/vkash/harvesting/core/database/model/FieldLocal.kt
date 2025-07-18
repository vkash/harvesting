package ua.com.vkash.harvesting.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fields")
data class FieldLocal(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    val id: Int = 0,
    @ColumnInfo(name = "guid")
    val guid: String = "",
    @ColumnInfo(name = "name")
    val name: String = ""
)
