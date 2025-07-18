package ua.com.vkash.harvesting.feature.harvest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ua.com.vkash.harvesting.core.domain.HarvestRepository
import javax.inject.Inject

@HiltViewModel
class HarvestsViewModel @Inject constructor(
    harvestRepository: HarvestRepository
) : ViewModel() {
    val uiState = harvestRepository.getHarvests().map(HarvestsUiState::Success).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HarvestsUiState.Loading
    )
}
