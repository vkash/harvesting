package ua.com.vkash.harvesting.feature.fields

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ua.com.vkash.harvesting.core.domain.FieldRepository
import ua.com.vkash.harvesting.feature.fields.navigation.SquaresRoute

@HiltViewModel
class SquaresScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    fieldRepository: FieldRepository
) : ViewModel() {
    private val args = savedStateHandle.toRoute<SquaresRoute>()

    val uiState = fieldRepository.getSquares(args.fieldId).map(SquaresUiState::Success).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SquaresUiState.Loading
    )
}
