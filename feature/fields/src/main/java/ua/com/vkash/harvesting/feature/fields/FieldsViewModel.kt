package ua.com.vkash.harvesting.feature.fields

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ua.com.vkash.harvesting.core.domain.FieldRepository

@HiltViewModel
class FieldsViewModel @Inject constructor(
    fieldRepository: FieldRepository
) : ViewModel() {
    val uiState = fieldRepository.getFields().map(FieldsUiState::Success).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = FieldsUiState.Loading
    )
}
