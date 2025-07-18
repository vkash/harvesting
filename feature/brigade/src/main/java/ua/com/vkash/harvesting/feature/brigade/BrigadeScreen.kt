package ua.com.vkash.harvesting.feature.brigade

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ua.com.vkash.harvesting.core.designsystem.component.SaveButton
import ua.com.vkash.harvesting.core.designsystem.theme.HarvestingTheme
import ua.com.vkash.harvesting.core.model.data.BrigadeDetail
import ua.com.vkash.harvesting.core.ui.ContentLoadingScreen
import ua.com.vkash.harvesting.core.ui.ListItemBrigadier
import ua.com.vkash.harvesting.core.ui.ListItemWorker

@Composable
fun BrigadeRoute(
    modifier: Modifier = Modifier,
    onFinish: () -> Unit = {},
    viewModel: BrigadeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { viewModel.listenForBarcodes() }

    BrigadeScreen(
        modifier = modifier,
        brigadeUiState = uiState,
        onFinish = onFinish,
        onSave = viewModel::saveBrigade
    )
}

@Composable
fun BrigadeScreen(
    modifier: Modifier = Modifier,
    onFinish: () -> Unit = {},
    onSave: () -> Unit = {},
    brigadeUiState: BrigadeUiState = BrigadeUiState.Loading
) {
    when (brigadeUiState) {
        BrigadeUiState.Loading -> ContentLoadingScreen()
        is BrigadeUiState.Success -> {
            if (brigadeUiState.finish) onFinish()
            WorkerList(
                modifier = modifier,
                data = brigadeUiState.data,
                readOnly = brigadeUiState.isReadOnly
            )
            AnimatedVisibility(
                visible = brigadeUiState.allowSave(),
                enter = slideInVertically { it / 2 }
            ) {
                SaveButton(
                    onClick = onSave
                )
            }
        }
    }
}

@Composable
fun WorkerList(
    modifier: Modifier = Modifier,
    readOnly: Boolean = true,
    data: BrigadeDetail = BrigadeDetail()
) {
    LazyColumn(modifier.fillMaxSize()) {
        stickyHeader(key = "brigadier") {
            ListItemBrigadier(
                name = data.brigadier?.name.orEmpty(),
                isEnabled = !readOnly
            )
        }
        itemsIndexed(data.items, key = { i, item -> item.id }) { i, item ->
            ListItemWorker(
                header = stringResource(R.string.worker_no, i + 1),
                name = item.workerName,
                isEnabled = !readOnly
            )
            HorizontalDivider()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BrigadeScreenPreview(modifier: Modifier = Modifier) {
    HarvestingTheme {
        BrigadeScreen(modifier = modifier, brigadeUiState = BrigadeUiState.Success())
    }
}
