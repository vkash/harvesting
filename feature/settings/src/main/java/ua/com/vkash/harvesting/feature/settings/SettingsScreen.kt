package ua.com.vkash.harvesting.feature.settings

import android.content.ClipData
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.PermDeviceInformation
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import ua.com.vkash.harvesting.core.designsystem.theme.HarvestingTheme

@Composable
fun SettingsRoute(
    modifier: Modifier = Modifier,
    onFieldClick: () -> Unit = {},
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val errorState by viewModel.errorState.collectAsStateWithLifecycle()
    val syncState by viewModel.syncState.collectAsStateWithLifecycle()
    val updateState by viewModel.updateState.collectAsStateWithLifecycle()
    val checkForUpdateState by viewModel.checkForUpdateState.collectAsStateWithLifecycle()

    var showDataLossDialog by rememberSaveable { mutableStateOf(false) }
    var showSyncDialog by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.checkForUpdate()
    }

    if (checkForUpdateState) {
        showDataLossDialog = false
        showSyncDialog = false

        UpdateDialog(
            syncUiState = updateState,
            onDismiss = {
                viewModel.updateDialogShowed()
            }
        )
    }

    if (showDataLossDialog) {
        DataLossDialog(
            onConfirm = {
                showDataLossDialog = false
                showSyncDialog = true
                viewModel.startSync()
            },
            onDismiss = {
                showDataLossDialog = false
            }
        )
    }

    if (showSyncDialog) {
        SyncDialog(syncUiState = syncState, onDismiss = {
            showSyncDialog = false
        })
    }

    val clipboard = LocalClipboard.current
    val scope = rememberCoroutineScope()

    SettingsScreen(
        modifier = modifier,
        settingsUiState = uiState,
        errorUiState = errorState,
        onDeviceIdClick = {
            scope.launch {
                val clipData = ClipData.newPlainText("Device Id", viewModel.getDeviceId())
                clipboard.setClipEntry(ClipEntry(clipData))
            }
        },
        onFieldClick = onFieldClick,
        onSyncClick = { showDataLossDialog = true }
    )
}

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onDeviceIdClick: () -> Unit = {},
    onFieldClick: () -> Unit = {},
    onSyncClick: () -> Unit = {},
    settingsUiState: SettingsUiState = SettingsUiState(),
    errorUiState: ErrorUiState = ErrorUiState.None
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        when (errorUiState) {
            is ErrorUiState.Error -> {
                stickyHeader(key = "error") {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                MaterialTheme.colorScheme.errorContainer,
                                MaterialTheme.shapes.small
                            )
                    ) {
                        Icon(
                            modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp),
                            imageVector = Icons.Default.WarningAmber,
                            contentDescription = null
                        )
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = errorUiState.message(),
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            ErrorUiState.None -> Unit
        }
        item(key = "auth") {
            GroupAuth(
                deviceId = settingsUiState.deviceId,
                userName = settingsUiState.userName,
                onDeviceIdClick = onDeviceIdClick,
                onSyncClick = onSyncClick
            )
        }
        item(key = "misc") {
            GroupMisc(
                fieldName = settingsUiState.fieldName,
                versionName = settingsUiState.versionName,
                onFieldClick = onFieldClick
            )
        }
    }
}

@Composable
fun GroupMisc(
    modifier: Modifier = Modifier,
    onFieldClick: () -> Unit = {},
    fieldName: String = "",
    versionName: String = ""
) {
    SettingsGroup(
        modifier = modifier,
        title = R.string.misc
    ) {
        SettingsTextItem(
            title = R.string.field,
            summary = fieldName.ifBlank { stringResource(R.string.not_specified) },
            icon = Icons.Default.Map,
            icon2 = Icons.AutoMirrored.Default.ArrowRight,
            onClick = onFieldClick
        )

        HorizontalDivider(Modifier.padding(horizontal = 16.dp))

        SettingsTextItem(
            title = R.string.version,
            summary = versionName,
            icon = Icons.Default.Android,
            enabled = false
        )
    }
}

@Composable
fun GroupAuth(
    modifier: Modifier = Modifier,
    onDeviceIdClick: () -> Unit = {},
    onSyncClick: () -> Unit = {},
    deviceId: String = "",
    userName: String = ""
) {
    var dpSize by remember { mutableStateOf(DpSize.Zero) }
    val density = LocalDensity.current

    SettingsGroup(
        modifier = modifier,
        title = R.string.authorization
    ) {
        SettingsTextItem(
            modifier = Modifier.onSizeChanged {
                dpSize = density.run {
                    DpSize(it.width.toDp(), it.height.toDp())
                }
            },
            title = R.string.device_id,
            summary = deviceId,
            icon = Icons.Default.PermDeviceInformation,
            onClick = onDeviceIdClick
        )

        HorizontalDivider(Modifier.padding(horizontal = 16.dp))

        SettingsTextItem(
            title = R.string.user,
            summary = userName.ifBlank { stringResource(R.string.scan_badge) },
            icon = Icons.Default.Person,
            icon2 = Icons.Default.QrCode,
            enabled = false
        )

        HorizontalDivider(Modifier.padding(horizontal = 16.dp))

        SettingsTextItem(
            modifier = Modifier.size(dpSize),
            title = R.string.start_sync,
            icon = Icons.Default.Sync,
            onClick = onSyncClick
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun SettingsScreenPreview(modifier: Modifier = Modifier) {
    HarvestingTheme {
        SettingsScreen(modifier = modifier)
    }
}
