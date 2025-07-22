package ua.com.vkash.harvesting.feature.timesheet

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ua.com.vkash.harvesting.core.domain.SettingsRepository
import ua.com.vkash.harvesting.core.domain.TimeSheetRepository
import ua.com.vkash.harvesting.core.domain.WorkerRepository
import ua.com.vkash.harvesting.core.scanner.ScannerManager
import ua.com.vkash.harvesting.core.sync.UploadWorkerSyncManager

@HiltViewModel
class TimeSheetViewModel @Inject constructor(
    private val uploadWorkerSyncManager: UploadWorkerSyncManager,
    private val scannerManager: ScannerManager,
    private val workerRepository: WorkerRepository,
    private val timeSheetRepository: TimeSheetRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    val uiState = timeSheetRepository.getTimeSheets().map(TimeSheetUiState::Success).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = TimeSheetUiState.Loading
    )

    private var listenForBarcodesJob: Job? = null

    @MainThread
    fun listenForBarcodes() {
        if (listenForBarcodesJob?.isActive == true) return
        listenForBarcodesJob = viewModelScope.launch {
            scannerManager.resultFlow.collect { (ok, barcode) ->
                if (ok) {
                    val worker = workerRepository.findWorker(barcode)
                    if (worker != null) {
                        timeSheetRepository.addTimeSheet(
                            workerId = worker.id,
                            fieldId = settingsRepository.getField()
                        )
                        uploadWorkerSyncManager.requestSync()
                    }
                }
            }
        }
    }
}
