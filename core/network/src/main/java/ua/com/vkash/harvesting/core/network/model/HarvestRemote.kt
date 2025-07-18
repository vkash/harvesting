package ua.com.vkash.harvesting.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HarvestRemote(
    @SerialName("guid")
    val guid: String = "",
    @SerialName("worker")
    val worker: String = "",
    @SerialName("sku")
    val sku: String = "",
    @SerialName("tara")
    val tara: String = "",
    @SerialName("field")
    val field: String = "",
    @SerialName("square")
    val square: String = "",
    @SerialName("weight")
    val weight: Double = 0.0,
    @SerialName("taraQty")
    val taraQty: Int = 0
)
