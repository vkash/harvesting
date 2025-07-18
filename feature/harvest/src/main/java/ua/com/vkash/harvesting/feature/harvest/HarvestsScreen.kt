package ua.com.vkash.harvesting.feature.harvest

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
import ua.com.vkash.harvesting.core.model.data.Harvest
import ua.com.vkash.harvesting.core.ui.ContentEmpty
import ua.com.vkash.harvesting.core.ui.ContentLoadingScreen

@Composable
fun HarvestsRoute(
    modifier: Modifier = Modifier,
    onHarvestSelect: (id: Int, date: Date) -> Unit = { id, date -> },
    viewModel: HarvestsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HarvestsScreen(
        modifier = modifier,
        harvestsUiState = uiState,
        onHarvestSelect = onHarvestSelect
    )
}

@Composable
fun HarvestsScreen(
    modifier: Modifier = Modifier,
    onHarvestSelect: (id: Int, date: Date) -> Unit = { id, date -> },
    harvestsUiState: HarvestsUiState = HarvestsUiState.Loading
) {
    when (harvestsUiState) {
        HarvestsUiState.Loading -> ContentLoadingScreen()
        is HarvestsUiState.Success -> if (harvestsUiState.isEmpty()) {
            ContentEmpty()
        } else {
            HarvestList(
                modifier = modifier,
                data = harvestsUiState.data,
                onClickItem = onHarvestSelect
            )
        }
    }
}

@Composable
fun HarvestList(
    modifier: Modifier = Modifier,
    onClickItem: (id: Int, date: Date) -> Unit = { id, date -> },
    data: List<Harvest> = emptyList()
) {
    LazyColumn(modifier.fillMaxSize()) {
        items(data, key = { it.id }) { item ->
            HarvestListItem(harvest = item, onClick = { onClickItem(item.id, item.date) })
            HorizontalDivider()
        }
    }
}

@Composable
fun HarvestListItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    harvest: Harvest = Harvest()
) {
    ListItem(
        modifier = modifier.clickable(onClick = onClick),
        headlineContent = {
            Text(
                text = stringResource(
                    id = R.string.num_date,
                    harvest.id,
                    harvest.date
                )
            )
        },
        trailingContent = {
            Icon(
                imageVector = when (harvest.entityState) {
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
private fun HarvestsScreenPreview(modifier: Modifier = Modifier) {
    HarvestingTheme {
        HarvestsScreen(modifier = modifier, harvestsUiState = HarvestsUiState.Success())
    }
}
