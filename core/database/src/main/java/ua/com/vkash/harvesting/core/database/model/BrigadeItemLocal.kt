package ua.com.vkash.harvesting.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "brigades_items",
    foreignKeys = [
        ForeignKey(
            entity = BrigadeLocal::class,
            parentColumns = ["rowid"],
            childColumns = ["brigade_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class BrigadeItemLocal(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    val id: Int = 0,
    @ColumnInfo(name = "brigade_id", index = true)
    val brigadeId: Int = 0,
    @ColumnInfo(name = "worker_id")
    val workerId: Int = 0
)
