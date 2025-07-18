package ua.com.vkash.harvesting.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FieldRemote(
    @SerialName("guid")
    val guid: String = "",
    @SerialName("name")
    val name: String = "",
    @SerialName("squares")
    val squares: List<SquareRemote> = emptyList()
)
