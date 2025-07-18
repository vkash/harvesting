package ua.com.vkash.harvesting.feature.works

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudQueue
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.util.Date
import ua.com.vkash.harvesting.core.designsystem.theme.HarvestingTheme
import ua.com.vkash.harvesting.core.model.data.EntityState
import ua.com.vkash.harvesting.core.model.data.Work
import ua.com.vkash.harvesting.core.ui.ContentEmpty
import ua.com.vkash.harvesting.core.ui.ContentLoadingScreen

@Composable
fun WorksRoute(
    modifier: Modifier = Modifier,
    onWorkSelect: (id: Int, date: Date) -> Unit = { id, date -> },
    viewModel: WorksViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    WorksScreen(
        modifier = modifier,
        worksUiState = uiState,
        onWorkSelect = onWorkSelect
    )
}

@Composable
fun WorksScreen(
    modifier: Modifier = Modifier,
    onWorkSelect: (id: Int, date: Date) -> Unit = { id, date -> },
    worksUiState: WorksUiState = WorksUiState.Loading
) {
    when (worksUiState) {
        WorksUiState.Loading -> ContentLoadingScreen()

        is WorksUiState.Success -> if (worksUiState.isEmpty()) {
            ContentEmpty()
        } else {
            WorksList(
                modifier = modifier,
                data = worksUiState.data,
                onClickItem = onWorkSelect
            )
        }
    }
}

@Composable
fun WorksList(
    modifier: Modifier = Modifier,
    onClickItem: (id: Int, date: Date) -> Unit = { id, date -> },
    data: List<Work> = emptyList()
) {
    LazyColumn(modifier.fillMaxSize()) {
        items(data, key = { it.id }) { item ->
            WorksListItem(work = item, onClick = { onClickItem(item.id, item.date) })
            HorizontalDivider()
        }
    }
}

@Composable
fun WorksListItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    work: Work = Work()
) {
    ListItem(
        modifier = modifier.clickable(onClick = onClick),
        headlineContent = {
            Text(
                text = stringResource(R.string.num_date, work.id, work.date)
            )
        },
        trailingContent = {
            Icon(
                imageVector = when (work.entityState) {
                    EntityState.CREATED -> Icons.Filled.CloudQueue
                    EntityState.SAVED -> Icons.Filled.CloudUpload
                    EntityState.SENT -> Icons.Filled.CloudDone
                },
                contentDescription = null
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun WorksScreenPreview(modifier: Modifier = Modifier) {
    HarvestingTheme {
        WorksScreen(modifier = modifier, worksUiState = WorksUiState.Success())
    }
}
