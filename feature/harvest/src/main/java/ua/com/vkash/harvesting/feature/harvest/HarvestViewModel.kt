package ua.com.vkash.harvesting.feature.harvest

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
import ua.com.vkash.harvesting.core.domain.FieldRepository
import ua.com.vkash.harvesting.core.domain.HarvestRepository
import ua.com.vkash.harvesting.core.domain.SettingsRepository
import ua.com.vkash.harvesting.core.domain.WorkerRepository
import ua.com.vkash.harvesting.core.scanner.ScannerManager
import ua.com.vkash.harvesting.core.sync.UploadWorkerSyncManager
import ua.com.vkash.harvesting.feature.harvest.navigation.HarvestRoute

@HiltViewModel
class HarvestViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val uploadWorkerSyncManager: UploadWorkerSyncManager,
    private val scannerManager: ScannerManager,
    private val fieldRepository: FieldRepository,
    private val harvestRepository: HarvestRepository,
    private val workerRepository: WorkerRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    private val harvestRoute = savedStateHandle.toRoute<HarvestRoute>()

    private val finish = MutableStateFlow(false)

    val uiState =
        combine(harvestRepository.getHarvest(harvestRoute.id), finish, HarvestUiState::Success)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HarvestUiState.Loading
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
                    if (worker != null) {
                        harvestRepository.updateWorker(worker.id, harvestRoute.id)
                    }
                }
            }
        }
    }

    fun saveHarvest() {
        viewModelScope.launch {
            harvestRepository.setSaved(harvestRoute.id)
            uploadWorkerSyncManager.requestSync()
            finish.value = true
        }
    }

    fun updateWeight(value: Double) {
        viewModelScope.launch {
            harvestRepository.updateWeight(value, harvestRoute.id)
        }
    }

    fun updateTaraQty(value: Double) {
        viewModelScope.launch {
            harvestRepository.updateTaraQty(value.toInt(), harvestRoute.id)
        }
    }

    fun updateSquare(squareId: Int) {
        viewModelScope.launch {
            val square = fieldRepository.getSquare(squareId)
            if (square != null) {
                harvestRepository.updateSquare(squareId, square.skuId, harvestRoute.id)
                settingsRepository.setSquare(squareId)
                settingsRepository.setSku(square.skuId)
            }
        }
    }

    fun updateTara(taraId: Int) {
        viewModelScope.launch {
            harvestRepository.updateTara(taraId, harvestRoute.id)
            settingsRepository.setTara(taraId)
        }
    }
}
