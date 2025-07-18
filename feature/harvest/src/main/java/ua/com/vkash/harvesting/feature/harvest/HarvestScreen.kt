package ua.com.vkash.harvesting.feature.harvest

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import ua.com.vkash.harvesting.core.model.data.HarvestDetail
import ua.com.vkash.harvesting.core.ui.ContentLoadingScreen
import ua.com.vkash.harvesting.core.ui.ListItemField
import ua.com.vkash.harvesting.core.ui.ListItemQty
import ua.com.vkash.harvesting.core.ui.ListItemSku
import ua.com.vkash.harvesting.core.ui.ListItemSquare
import ua.com.vkash.harvesting.core.ui.ListItemTara
import ua.com.vkash.harvesting.core.ui.ListItemWeight
import ua.com.vkash.harvesting.core.ui.ListItemWorker

@Composable
fun HarvestRoute(
    modifier: Modifier = Modifier,
    onFinish: () -> Unit = {},
    onSquareSelect: (fieldId: Int) -> Unit = { },
    onTaraSelect: () -> Unit = { },
    onWeightSelect: (Double) -> Unit = { },
    onQtySelect: (Int) -> Unit = { },
    viewModel: HarvestViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { viewModel.listenForBarcodes() }

    HarvestScreen(
        modifier = modifier,
        harvestUiState = uiState,
        onSave = viewModel::saveHarvest,
        onFinish = onFinish,
        onSquareSelect = onSquareSelect,
        onTaraSelect = onTaraSelect,
        onWeightSelect = onWeightSelect,
        onQtySelect = onQtySelect
    )
}

@Composable
fun HarvestScreen(
    modifier: Modifier = Modifier,
    onSave: () -> Unit = {},
    onFinish: () -> Unit = {},
    onSquareSelect: (fieldId: Int) -> Unit = { },
    onTaraSelect: () -> Unit = { },
    onWeightSelect: (Double) -> Unit = { },
    onQtySelect: (Int) -> Unit = { },
    harvestUiState: HarvestUiState = HarvestUiState.Loading
) {
    when (harvestUiState) {
        HarvestUiState.Loading -> ContentLoadingScreen()
        is HarvestUiState.Success -> {
            if (harvestUiState.finish) onFinish()
            DataScreen(
                modifier = modifier.verticalScroll(rememberScrollState()),
                readOnly = harvestUiState.isReadOnly,
                harvestDetail = harvestUiState.data,
                onSquareSelect = onSquareSelect,
                onTaraSelect = onTaraSelect,
                onWeightSelect = onWeightSelect,
                onQtySelect = onQtySelect
            )
            AnimatedVisibility(
                visible = harvestUiState.allowSave(),
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
fun DataScreen(
    modifier: Modifier = Modifier,
    readOnly: Boolean = true,
    onSquareSelect: (fieldId: Int) -> Unit = { },
    onTaraSelect: () -> Unit = { },
    onWeightSelect: (Double) -> Unit = { },
    onQtySelect: (Int) -> Unit = { },
    harvestDetail: HarvestDetail = HarvestDetail()
) {
    Column(modifier = modifier.wrapContentHeight()) {
        ListItemWorker(
            name = harvestDetail.worker?.name.orEmpty(),
            isEnabled = !readOnly
        )
        ListItemField(name = harvestDetail.field?.name.orEmpty())
        ListItemSquare(
            name = harvestDetail.square?.name.orEmpty(),
            isEnabled = !readOnly,
            onClick = {
                onSquareSelect(harvestDetail.field?.id ?: 0)
            }
        )
        ListItemSku(name = harvestDetail.sku?.name.orEmpty())
        ListItemTara(
            name = harvestDetail.tara?.name.orEmpty(),
            isEnabled = !readOnly,
            onClick = onTaraSelect
        )
        ListItemWeight(
            qty = harvestDetail.harvest.weight,
            isEnabled = !readOnly,
            onClick = onWeightSelect
        )
        ListItemQty(
            header = stringResource(R.string.tara_qty),
            qty = harvestDetail.harvest.taraQty,
            isEnabled = !readOnly,
            onClick = onQtySelect
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HarvestScreenPreview(modifier: Modifier = Modifier) {
    HarvestingTheme {
        HarvestScreen(modifier = modifier, harvestUiState = HarvestUiState.Success())
    }
}
