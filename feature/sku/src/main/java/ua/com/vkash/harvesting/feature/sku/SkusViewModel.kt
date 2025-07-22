package ua.com.vkash.harvesting.feature.sku

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import ua.com.vkash.harvesting.core.domain.SettingsRepository
import ua.com.vkash.harvesting.core.domain.SkuRepository
import ua.com.vkash.harvesting.feature.sku.navigation.SKU
import ua.com.vkash.harvesting.feature.sku.navigation.SkusRoute
import ua.com.vkash.harvesting.feature.sku.navigation.TARA

@HiltViewModel
class SkusViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    skuRepository: SkuRepository,
    settingsRepository: SettingsRepository
) : ViewModel() {
    private val args = savedStateHandle.toRoute<SkusRoute>()
    private val skuType = flowOf(args.type)

    val skusUiState = combine(skuType, settingsRepository.fieldFlow()) { t, f ->
        val data = when (t) {
            SKU -> skuRepository.getSku(f)
            TARA -> skuRepository.getTara()
            else -> skuRepository.getSku()
        }
        SkusUiState.Success(data)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SkusUiState.Loading
    )
}
