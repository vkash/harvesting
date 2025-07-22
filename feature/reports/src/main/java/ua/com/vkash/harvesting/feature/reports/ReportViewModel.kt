package ua.com.vkash.harvesting.feature.reports

import androidx.annotation.MainThread
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import ua.com.vkash.harvesting.core.common.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ua.com.vkash.harvesting.core.domain.ReportRepository
import ua.com.vkash.harvesting.core.domain.SettingsRepository
import ua.com.vkash.harvesting.core.scanner.ScannerManager
import ua.com.vkash.harvesting.feature.reports.navigation.ReportRoute

@HiltViewModel
class ReportViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val scannerManager: ScannerManager,
    private val reportRepository: ReportRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    private val reportId = savedStateHandle.toRoute<ReportRoute>().id

    private val html = MutableStateFlow("")

    val uiState = html.map(ReportUiState::Success).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ReportUiState.Loading
    )

    private var listenForBarcodesJob: Job? = null

    @MainThread
    fun listenForBarcodes() {
        if (listenForBarcodesJob?.isActive == true) return
        listenForBarcodesJob = viewModelScope.launch {
            scannerManager.resultFlow.collect { (ok, barcode) ->
                if (ok) getReport(barcode)
            }
        }
    }

    private fun getReport(workerBarcode: String) {
        viewModelScope.launch {
            val userBarcode = settingsRepository.getUserBarcode()
            when (
                val response =
                    reportRepository.fetchReport(userBarcode, reportId, workerBarcode)
            ) {
                is ApiResult.Success -> {
                    html.value = response.data
                }

                else -> Unit
            }
        }
    }
}
