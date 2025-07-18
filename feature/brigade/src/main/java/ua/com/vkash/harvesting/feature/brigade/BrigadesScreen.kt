package ua.com.vkash.harvesting.feature.brigade

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
import ua.com.vkash.harvesting.core.model.data.Brigade
import ua.com.vkash.harvesting.core.model.data.EntityState
import ua.com.vkash.harvesting.core.ui.ContentEmpty
import ua.com.vkash.harvesting.core.ui.ContentLoadingScreen

@Composable
fun BrigadesRoute(
    modifier: Modifier = Modifier,
    viewModel: BrigadesViewModel = hiltViewModel(),
    onBrigadeSelect: (id: Int, date: Date) -> Unit = { id, date -> }
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BrigadesScreen(
        modifier = modifier,
        brigadesUiState = uiState,
        onBrigadeSelect = onBrigadeSelect
    )
}

@Composable
fun BrigadesScreen(
    modifier: Modifier = Modifier,
    onBrigadeSelect: (id: Int, date: Date) -> Unit = { id, date -> },
    brigadesUiState: BrigadesUiState = BrigadesUiState.Loading
) {
    when (brigadesUiState) {
        BrigadesUiState.Loading -> ContentLoadingScreen()
        is BrigadesUiState.Success -> if (brigadesUiState.isEmpty()) {
            ContentEmpty()
        } else {
            BrigadeList(
                modifier = modifier,
                data = brigadesUiState.data,
                onClickItem = onBrigadeSelect
            )
        }
    }
}

@Composable
fun BrigadeList(
    modifier: Modifier = Modifier,
    onClickItem: (id: Int, date: Date) -> Unit = { id, date -> },
    data: List<Brigade> = emptyList()
) {
    LazyColumn(modifier.fillMaxSize()) {
        items(data, key = { it.id }) { item ->
            BrigadeListItem(brigade = item, onClick = { onClickItem(item.id, item.date) })
            HorizontalDivider()
        }
    }
}

@Composable
fun BrigadeListItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    brigade: Brigade = Brigade()
) {
    ListItem(
        modifier = modifier.clickable(onClick = onClick),
        headlineContent = {
            Text(
                text = stringResource(
                    id = R.string.num_date,
                    brigade.id,
                    brigade.date
                )
            )
        },
        trailingContent = {
            Icon(
                imageVector = when (brigade.entityState) {
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
private fun BrigadesScreenPreview(modifier: Modifier = Modifier) {
    HarvestingTheme {
        BrigadesScreen(modifier = modifier, brigadesUiState = BrigadesUiState.Success())
    }
}
