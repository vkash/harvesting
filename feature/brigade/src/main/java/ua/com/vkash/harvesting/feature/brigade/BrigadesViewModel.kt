package ua.com.vkash.harvesting.feature.brigade

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ua.com.vkash.harvesting.core.domain.BrigadeRepository

@HiltViewModel
class BrigadesViewModel @Inject constructor(
    brigadeRepository: BrigadeRepository
) : ViewModel() {
    val uiState = brigadeRepository.getBrigades().map(BrigadesUiState::Success).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = BrigadesUiState.Loading
    )
}
