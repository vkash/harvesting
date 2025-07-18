package ua.com.vkash.harvesting.core.model.data

data class User(
    val guid: String = "",
    val name: String = "",
    val barcode: String = "",
    val type: UserType = UserType.Unknown
)

enum class UserType(val id: Int) {
    Unknown(-1),
    Brigadier(0),
    Guardian(1);

    companion object {
        fun by(id: Int) = entries.firstOrNull { it.id == id } ?: Unknown
    }
}
