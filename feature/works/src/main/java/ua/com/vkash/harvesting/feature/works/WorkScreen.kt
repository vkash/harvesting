package ua.com.vkash.harvesting.feature.works

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ua.com.vkash.harvesting.core.designsystem.component.SaveButton
import ua.com.vkash.harvesting.core.designsystem.theme.HarvestingTheme
import ua.com.vkash.harvesting.core.model.data.WorkDetail
import ua.com.vkash.harvesting.core.ui.ContentLoadingScreen
import ua.com.vkash.harvesting.core.ui.ListItemBrigadier
import ua.com.vkash.harvesting.core.ui.ListItemField
import ua.com.vkash.harvesting.core.ui.ListItemQty
import ua.com.vkash.harvesting.core.ui.ListItemSquare
import ua.com.vkash.harvesting.core.ui.ListItemWorkType

@Composable
fun WorkRoute(
    modifier: Modifier = Modifier,
    onWorkTypeSelect: () -> Unit = { },
    onSquareSelect: (fieldId: Int) -> Unit = { },
    onQtySelect: (Double) -> Unit = { },
    onFinish: () -> Unit = {},
    viewModel: WorkViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { viewModel.listenForBarcodes() }

    WorkScreen(
        modifier = modifier,
        workUiState = uiState,
        onWorkTypeSelect = onWorkTypeSelect,
        onSquareSelect = onSquareSelect,
        onQtySelect = onQtySelect,
        onSave = viewModel::saveWork,
        onFinish = onFinish
    )
}

@Composable
fun WorkScreen(
    modifier: Modifier = Modifier,
    onWorkTypeSelect: () -> Unit = { },
    onSquareSelect: (fieldId: Int) -> Unit = { },
    onQtySelect: (Double) -> Unit = { },
    onSave: () -> Unit = {},
    onFinish: () -> Unit = {},
    workUiState: WorkUiState = WorkUiState.Loading
) {
    when (workUiState) {
        WorkUiState.Loading -> ContentLoadingScreen()
        is WorkUiState.Success -> {
            if (workUiState.finish) onFinish()
            DataScreen(
                modifier = modifier.verticalScroll(rememberScrollState()),
                readOnly = workUiState.isReadOnly,
                workDetail = workUiState.data,
                onWorkTypeSelect = onWorkTypeSelect,
                onSquareSelect = onSquareSelect,
                onQtySelect = onQtySelect
            )
            AnimatedVisibility(
                visible = workUiState.allowSave(),
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
    onWorkTypeSelect: () -> Unit = { },
    onSquareSelect: (fieldId: Int) -> Unit = { },
    onQtySelect: (Double) -> Unit = { },
    workDetail: WorkDetail = WorkDetail()
) {
    Column(modifier = modifier.wrapContentHeight()) {
        ListItemBrigadier(
            name = workDetail.brigadier?.name.orEmpty(),
            isEnabled = !readOnly
        )
        ListItemWorkType(
            name = workDetail.workType?.name.orEmpty(),
            isEnabled = !readOnly,
            onClick = onWorkTypeSelect
        )
        ListItemField(name = workDetail.field?.name.orEmpty())
        ListItemSquare(
            name = workDetail.square?.name.orEmpty(),
            isEnabled = !readOnly,
            onClick = {
                onSquareSelect(workDetail.field?.id ?: 0)
            }
        )
        ListItemQty(
            qty = workDetail.work.qty,
            isEnabled = !readOnly,
            onClick = onQtySelect
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WorkScreenPreview(modifier: Modifier = Modifier) {
    HarvestingTheme {
        WorkScreen(modifier = modifier, workUiState = WorkUiState.Success())
    }
}
