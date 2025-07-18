package ua.com.vkash.harvesting.core.model.data

data class BrigadeDetail(
    val brigade: Brigade = Brigade(),
    val brigadier: Worker? = null,
    val items: List<BrigadeItemDetail> = emptyList()
) {
    fun check() = brigadier != null && items.isNotEmpty()

    val isReadOnly: Boolean
        get() = brigade.entityState != EntityState.CREATED
}
