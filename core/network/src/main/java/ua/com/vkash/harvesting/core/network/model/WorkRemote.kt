package ua.com.vkash.harvesting.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WorkRemote(
    @SerialName("guid")
    val guid: String = "",
    @SerialName("brigadier")
    val brigadier: String = "",
    @SerialName("field")
    val field: String = "",
    @SerialName("square")
    val square: String = "",
    @SerialName("work_type")
    val workType: String = "",
    @SerialName("qty")
    val qty: Double = 0.0
)
