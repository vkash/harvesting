package ua.com.vkash.harvesting.core.model.data

data class WorkDetail(
    val work: Work = Work(),
    val brigadier: Worker? = null,
    val workType: WorkType? = null,
    val field: Field? = null,
    val square: Square? = null
) {
    fun check() =
        brigadier != null && workType != null && field != null && square != null && work.qty > 0.0

    val isReadOnly: Boolean
        get() = work.entityState != EntityState.CREATED
}
