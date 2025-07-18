package ua.com.vkash.harvesting.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SkuRemote(
    @SerialName("guid")
    val guid: String = "",
    @SerialName("name")
    val name: String = "",
    @SerialName("skutype")
    val skuType: Int = 0
)
