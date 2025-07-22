package ua.com.vkash.harvesting.feature.works

import androidx.annotation.MainThread
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ua.com.vkash.harvesting.core.domain.SettingsRepository
import ua.com.vkash.harvesting.core.domain.WorkRepository
import ua.com.vkash.harvesting.core.domain.WorkerRepository
import ua.com.vkash.harvesting.core.scanner.ScannerManager
import ua.com.vkash.harvesting.core.sync.UploadWorkerSyncManager
import ua.com.vkash.harvesting.feature.works.navigation.WorkRoute

@HiltViewModel
class WorkViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val uploadWorkerSyncManager: UploadWorkerSyncManager,
    private val scannerManager: ScannerManager,
    private val workRepository: WorkRepository,
    private val workerRepository: WorkerRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    private val workRoute = savedStateHandle.toRoute<WorkRoute>()
    private val finish = MutableStateFlow(false)

    val uiState = combine(workRepository.getWork(workRoute.id), finish, WorkUiState::Success)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = WorkUiState.Loading
        )

    private var listenForBarcodesJob: Job? = null

    @MainThread
    fun listenForBarcodes() {
        if (listenForBarcodesJob?.isActive == true) return
        listenForBarcodesJob = viewModelScope.launch {
            scannerManager.resultFlow.filterNot {
                uiState.value.isReadOnly
            }.collect { (ok, barcode) ->
                if (ok) {
                    val worker = workerRepository.findWorker(barcode)
                    if (worker != null) {
                        workRepository.updateBrigadier(worker.id, workRoute.id)
                    }
                }
            }
        }
    }

    fun saveWork() {
        viewModelScope.launch {
            workRepository.setSaved(workRoute.id)
            uploadWorkerSyncManager.requestSync()
            finish.value = true
        }
    }

    fun updateWorkQty(value: Double) {
        viewModelScope.launch {
            workRepository.updateWorkQty(value, workRoute.id)
        }
    }

    fun updateSquare(squareId: Int) {
        viewModelScope.launch {
            workRepository.updateSquare(squareId, workRoute.id)
            settingsRepository.setSquare(squareId)
        }
    }

    fun updateWorkType(workTypeId: Int) {
        viewModelScope.launch {
            workRepository.updateWorkType(workTypeId, workRoute.id)
        }
    }
}
