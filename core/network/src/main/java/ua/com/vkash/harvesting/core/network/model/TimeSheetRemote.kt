package ua.com.vkash.harvesting.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ua.com.vkash.harvesting.core.network.DateAsString

@Serializable
data class TimeSheetRemote(
    @SerialName("worker")
    val worker: String = "",
    @SerialName("field")
    val field: String = "",
    @SerialName("time")
    val time: DateAsString = DateAsString(0L)
)
