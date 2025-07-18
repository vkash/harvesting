package ua.com.vkash.harvesting.core.model.data

data class HarvestDetail(
    val harvest: Harvest = Harvest(),
    val worker: Worker? = null,
    val sku: Sku? = null,
    val tara: Sku? = null,
    val field: Field? = null,
    val square: Square? = null
) {
    fun check() =
        worker != null &&
            sku != null &&
            tara != null &&
            field != null &&
            square != null &&
            harvest.weight > 0.0 &&
            harvest.taraQty > 0

    val isReadOnly: Boolean
        get() = harvest.entityState != EntityState.CREATED
}
