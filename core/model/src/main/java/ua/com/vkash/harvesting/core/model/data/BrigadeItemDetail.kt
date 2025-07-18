package ua.com.vkash.harvesting.core.model.data

data class BrigadeItemDetail(
    val id: Int,
    val brigadeId: Int,
    val workerId: Int,
    val workerName: String,
    val workerBarcode: String,
    val workerGuid: String
)
