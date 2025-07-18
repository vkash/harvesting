package ua.com.vkash.harvesting.core.data.repository

import com.vkash.harvesting.core.common.di.IoDispatcher
import dagger.Lazy
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import ua.com.vkash.harvesting.core.domain.ReportRepository
import ua.com.vkash.harvesting.core.model.data.Report
import ua.com.vkash.harvesting.core.model.data.ReportId
import ua.com.vkash.harvesting.core.network.ApiClient

class ReportRepositoryImpl @Inject constructor(
    private val apiClient: Lazy<ApiClient>,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ReportRepository {
    private val reports = listOf(
        Report(ReportId.HarvestToday),
        Report(ReportId.HarvestByBrigade)
    )

    override fun getReports() = flowOf(reports)

    override suspend fun fetchReport(
        barcode: String,
        id: String,
        workerBarcode: String
    ) = withContext(dispatcher) {
        apiClient.get().getReport(barcode, id, workerBarcode)
    }
}
