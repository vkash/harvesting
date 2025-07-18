package ua.com.vkash.harvesting.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import ua.com.vkash.harvesting.core.domain.BrigadeRepository
import ua.com.vkash.harvesting.core.domain.HarvestRepository
import ua.com.vkash.harvesting.core.domain.SettingsRepository
import ua.com.vkash.harvesting.core.domain.WorkRepository

@HiltViewModel
class HarvestingAppViewModel @Inject constructor(
    private val brigadeRepository: BrigadeRepository,
    private val harvestRepository: HarvestRepository,
    private val workRepository: WorkRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    fun createBrigade(onCreated: (id: Int) -> Unit) {
        viewModelScope.launch {
            val id = brigadeRepository.addBrigade()
            onCreated(id.toInt())
        }
    }

    fun createHarvest(onCreated: (id: Int) -> Unit) {
        viewModelScope.launch {
            val id = harvestRepository.addHarvest(
                skuId = settingsRepository.getSku(),
                taraId = settingsRepository.getTara(),
                fieldId = settingsRepository.getField(),
                squareId = settingsRepository.getSquare()
            )
            onCreated(id.toInt())
        }
    }

    fun createWork(onCreated: (id: Int) -> Unit) {
        viewModelScope.launch {
            val id = workRepository.addWork(
                fieldId = settingsRepository.getField(),
                squareId = settingsRepository.getSquare()
            )
            onCreated(id.toInt())
        }
    }
}
