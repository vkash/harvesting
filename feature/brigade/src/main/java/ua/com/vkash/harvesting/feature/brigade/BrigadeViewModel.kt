package ua.com.vkash.harvesting.feature.brigade

import androidx.annotation.MainThread
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ua.com.vkash.harvesting.core.domain.BrigadeRepository
import ua.com.vkash.harvesting.core.domain.WorkerRepository
import ua.com.vkash.harvesting.core.scanner.ScannerManager
import ua.com.vkash.harvesting.core.sync.UploadWorkerSyncManager
import ua.com.vkash.harvesting.feature.brigade.navigation.BrigadeRoute

@HiltViewModel
class BrigadeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val uploadWorkerSyncManager: UploadWorkerSyncManager,
    private val scannerManager: ScannerManager,
    private val brigadeRepository: BrigadeRepository,
    private val workerRepository: WorkerRepository
) : ViewModel() {
    private val brigadeRoute = savedStateHandle.toRoute<BrigadeRoute>()
    private val finish = MutableStateFlow(false)

    val uiState = combine(
        brigadeRepository.getBrigade(brigadeRoute.id),
        finish,
        BrigadeUiState::Success
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = BrigadeUiState.Loading
    )

    private var listeningForBarcodes = false

    @MainThread
    fun listenForBarcodes() {
        if (listeningForBarcodes) return
        listeningForBarcodes = true
        viewModelScope.launch {
            scannerManager.resultFlow.filterNot {
                uiState.value.isReadOnly
            }.collect { (ok, barcode) ->
                if (ok) {
                    val worker = workerRepository.findWorker(barcode)
                    if (worker == null) return@collect

                    val uiStateValue = uiState.value
                    if (uiStateValue is BrigadeUiState.Success) {
                        if (uiStateValue.data.brigadier == null) {
                            brigadeRepository.updateBrigadier(
                                workerId = worker.id,
                                brigadeId = brigadeRoute.id
                            )
                        } else {
                            brigadeRepository.addBrigadeWorker(
                                workerId = worker.id,
                                brigadeId = brigadeRoute.id
                            )
                        }
                    }
                }
            }
        }
    }

    fun saveBrigade() {
        viewModelScope.launch {
            brigadeRepository.setSaved(brigadeRoute.id)
            uploadWorkerSyncManager.requestSync()
            finish.value = true
        }
    }
}
