package ua.com.vkash.harvesting.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @param type - 0(brigadier), 1(guardian), -1(unknown)
 */
@Serializable
data class UserRemote(
    @SerialName("guid")
    val guid: String = "",
    @SerialName("name")
    val name: String = "",
    @SerialName("barcode")
    val barcode: String = "",
    @SerialName("type")
    val type: Int = -1
)
