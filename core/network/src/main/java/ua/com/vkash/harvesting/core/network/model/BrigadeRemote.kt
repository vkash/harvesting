package ua.com.vkash.harvesting.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BrigadeRemote(
    @SerialName("guid")
    val guid: String = "",
    @SerialName("brigadier")
    val brigadier: String = "",
    @SerialName("workers")
    val workers: List<String> = emptyList()
)