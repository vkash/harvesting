package ua.com.vkash.harvesting.feature.works

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ua.com.vkash.harvesting.core.domain.WorkRepository

@HiltViewModel
class WorksViewModel @Inject constructor(
    workRepository: WorkRepository
) : ViewModel() {
    val uiState = workRepository.getWorks().map(WorksUiState::Success).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = WorksUiState.Loading
    )
}
