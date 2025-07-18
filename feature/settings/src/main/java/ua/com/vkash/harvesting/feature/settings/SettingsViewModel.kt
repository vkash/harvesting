package ua.com.vkash.harvesting.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vkash.harvesting.core.common.ApiResult
import com.vkash.harvesting.core.common.AppInfo
import com.vkash.harvesting.core.common.di.ApplicationInfo
import com.vkash.harvesting.core.common.di.DeviceId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ua.com.vkash.harvesting.core.domain.FieldRepository
import ua.com.vkash.harvesting.core.domain.SettingsRepository
import ua.com.vkash.harvesting.core.domain.UpdateRepository
import ua.com.vkash.harvesting.core.domain.UserRepository
import ua.com.vkash.harvesting.core.scanner.ScannerManager
import ua.com.vkash.harvesting.core.sync.DownloadWorkerSyncManager
import ua.com.vkash.harvesting.core.sync.SyncState
import ua.com.vkash.harvesting.core.sync.UpgradeWorkerSyncManager
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
@Inject
constructor(
    @DeviceId private val deviceId: String,
    @ApplicationInfo private val appInfo: AppInfo,
    scannerManager: ScannerManager,
    private val downloadWorkerSyncManager: DownloadWorkerSyncManager,
    private val upgradeWorkerSyncManager: UpgradeWorkerSyncManager,
    private val fieldRepository: FieldRepository,
    private val userRepository: UserRepository,
    private val settingsRepository: SettingsRepository,
    private val updateRepository: UpdateRepository
) : ViewModel() {
    val errorState =
        scannerManager.resultFlow
            .map { (ok, barcode) ->
                if (ok) {
                    when (val response = userRepository.fetchUser(barcode)) {
                        is ApiResult.Success -> {
                            settingsRepository.setUser(
                                name = response.data.name,
                                type = response.data.type.id,
                                barcode = response.data.barcode
                            )
                            ErrorUiState.None
                        }

                        is ApiResult.ServerError -> ErrorUiState.Error(response.error)
                        is ApiResult.NetworkError -> ErrorUiState.Error(response.error)
                        is ApiResult.UnknownError -> ErrorUiState.Error()
                        is ApiResult.OutdatedApp -> ErrorUiState.Error(
                            msgId = R.string.outdated_app
                        )
                        is ApiResult.Unauthorised -> ErrorUiState.Error(msgId = R.string.auth_error)
                    }
                } else {
                    ErrorUiState.Error(msgId = R.string.scanner_error)
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ErrorUiState.None
            )

    val uiState =
        combine(
            settingsRepository.userNameFlow(),
            settingsRepository.fieldFlow()
        ) { u, f ->
            val field = fieldRepository.getField(f)
            SettingsUiState(
                deviceId = deviceId,
                userName = u,
                fieldName = field?.name.orEmpty(),
                versionName = appInfo.versionName
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SettingsUiState(deviceId = deviceId)
        )

    val syncState = downloadWorkerSyncManager.syncInfo.map { syncInfo ->
        when (syncInfo.state) {
            SyncState.IDLE -> SyncUiState.Ready
            SyncState.SYNCING -> SyncUiState.Process(
                progress = syncInfo.progress,
                message = syncInfo.message
            )

            SyncState.DONE -> SyncUiState.Done
            SyncState.ERROR -> SyncUiState.Error(message = syncInfo.message)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SyncUiState.Ready
    )

    val updateState = upgradeWorkerSyncManager.syncInfo.map { syncInfo ->
        when (syncInfo.state) {
            SyncState.IDLE -> SyncUiState.Ready
            SyncState.SYNCING -> SyncUiState.Process(
                progress = syncInfo.progress,
                message = syncInfo.message
            )

            SyncState.DONE -> SyncUiState.Done
            SyncState.ERROR -> SyncUiState.Error(message = syncInfo.message)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SyncUiState.Ready
    )

    private val _checkForUpdateState = MutableStateFlow(false)
    val checkForUpdateState = _checkForUpdateState.asStateFlow()

    private var checkedForUpdate = false

    fun checkForUpdate() {
        if (!checkedForUpdate) {
            checkedForUpdate = true
            viewModelScope.launch {
                val checkUpdate = updateRepository.checkUpdate()
                if (checkUpdate) startUpdate()
                _checkForUpdateState.value = checkUpdate
            }
        }
    }

    fun updateDialogShowed() {
        _checkForUpdateState.value = false
    }

    fun startSync() {
        downloadWorkerSyncManager.requestSync()
    }

    private fun startUpdate() {
        upgradeWorkerSyncManager.requestSync()
    }

    fun selectField(fieldId: Int) {
        viewModelScope.launch {
            settingsRepository.setField(fieldId)
        }
    }

    fun getDeviceId() = deviceId
}
