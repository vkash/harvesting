package ua.com.vkash.harvesting.core.database.dto

import androidx.room.ColumnInfo
import androidx.room.DatabaseView

@DatabaseView(
    viewName = "brigades_items_detail",
    value = "select" +
        " i.rowid," +
        "i.brigade_id," +
        "i.worker_id," +
        "ifNull(w.name, '') as worker_name," +
        "ifNull(w.barcode, '') as worker_barcode," +
        "ifNull(w.guid, '') as worker_guid" +
        " from brigades_items i" +
        " left join workers w on i.worker_id=w.rowid"
)
data class BrigadeItemDetail(
    @ColumnInfo(name = "rowid")
    val id: Int,
    @ColumnInfo(name = "brigade_id")
    val brigadeId: Int,
    @ColumnInfo(name = "worker_id")
    val workerId: Int,
    @ColumnInfo(name = "worker_name")
    val workerName: String,
    @ColumnInfo(name = "worker_barcode")
    val workerBarcode: String,
    @ColumnInfo(name = "worker_guid")
    val workerGuid: String
)
