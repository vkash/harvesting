package ua.com.vkash.harvesting.feature.appmenu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ua.com.vkash.harvesting.core.common.AppInfo
import ua.com.vkash.harvesting.core.common.di.ApplicationInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ua.com.vkash.harvesting.core.domain.SettingsRepository
import ua.com.vkash.harvesting.core.model.data.UserType

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    @ApplicationInfo appInfo: AppInfo,
    settingsRepository: SettingsRepository
) : ViewModel() {
    val userName = settingsRepository.userNameFlow()
    val mainUiState = settingsRepository.userTypeFlow().map {
        if (appInfo.isDevDevice) {
            return@map MainUiState.Success(
                listOf(
                    MainCard.BRIGADES,
                    MainCard.HARVESTS,
                    MainCard.WORKS,
                    MainCard.REPORTS,
                    MainCard.TIMESHEET
                )
            )
        }
        val data = when (UserType.by(it)) {
            UserType.Brigadier -> listOf(
                MainCard.BRIGADES,
                MainCard.HARVESTS,
                MainCard.WORKS,
                MainCard.REPORTS
            )

            UserType.Guardian -> listOf(MainCard.TIMESHEET)
            else -> emptyList()
        }
        MainUiState.Success(data)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MainUiState.Loading
    )
}
