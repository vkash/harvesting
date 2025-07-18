package ua.com.vkash.harvesting.feature.works

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ua.com.vkash.harvesting.core.designsystem.theme.HarvestingTheme
import ua.com.vkash.harvesting.core.model.data.WorkType
import ua.com.vkash.harvesting.core.ui.ContentEmpty
import ua.com.vkash.harvesting.core.ui.ContentLoadingScreen

@Composable
fun WorkTypesRoute(
    modifier: Modifier = Modifier,
    viewModel: WorkTypesViewModel = hiltViewModel(),
    onSelectWorkType: (id: Int) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    WorkTypesScreen(
        modifier = modifier,
        workTypesUiState = uiState,
        onSelectWorkType = onSelectWorkType
    )
}

@Composable
fun WorkTypesScreen(
    modifier: Modifier = Modifier,
    workTypesUiState: WorkTypesUiState = WorkTypesUiState.Loading,
    onSelectWorkType: (id: Int) -> Unit = {}
) {
    when (workTypesUiState) {
        WorkTypesUiState.Loading -> ContentLoadingScreen()

        is WorkTypesUiState.Success -> if (workTypesUiState.isEmpty()) {
            ContentEmpty()
        } else {
            WorkTypeList(
                modifier = modifier,
                data = workTypesUiState.data,
                onClickItem = onSelectWorkType
            )
        }
    }
}

@Composable
fun WorkTypeList(
    modifier: Modifier = Modifier,
    data: List<WorkType> = emptyList(),
    onClickItem: (id: Int) -> Unit = {}
) {
    LazyColumn(modifier.fillMaxSize()) {
        items(data, key = { it.id }) { item ->
            WorkTypeItem(name = item.name, onClick = { onClickItem(item.id) })
            HorizontalDivider()
        }
    }
}

@Composable
fun WorkTypeItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    name: String = ""
) {
    ListItem(
        modifier = modifier.clickable(onClick = onClick),
        headlineContent = {
            Text(name)
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun WorkTypesScreenPreview(modifier: Modifier = Modifier) {
    HarvestingTheme {
        WorkTypesScreen(modifier, WorkTypesUiState.Success())
    }
}
