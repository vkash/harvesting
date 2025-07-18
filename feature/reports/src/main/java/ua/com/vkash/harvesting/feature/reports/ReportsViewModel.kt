package ua.com.vkash.harvesting.feature.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ua.com.vkash.harvesting.core.domain.ReportRepository

@HiltViewModel
class ReportsViewModel @Inject constructor(
    reportRepository: ReportRepository
) : ViewModel() {
    val uiState = reportRepository.getReports().map(ReportsUiState::Success).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ReportsUiState.Loading
    )
}
