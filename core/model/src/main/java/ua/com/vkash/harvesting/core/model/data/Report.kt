package ua.com.vkash.harvesting.core.model.data

data class Report(
    val reportId: ReportId
)

enum class ReportId(val id: String) {
    HarvestToday("harvest"),
    HarvestByBrigade("by_brigade")
}
