package ua.com.vkash.harvesting.feature.sku

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ua.com.vkash.harvesting.core.designsystem.theme.HarvestingTheme
import ua.com.vkash.harvesting.core.model.data.Sku
import ua.com.vkash.harvesting.core.ui.ContentEmpty
import ua.com.vkash.harvesting.core.ui.ContentLoadingScreen

@Composable
fun SkusRoute(
    modifier: Modifier = Modifier,
    viewModel: SkusViewModel = hiltViewModel(),
    onSkuSelect: (id: Int) -> Unit = {}
) {
    val skusUiState by viewModel.skusUiState.collectAsStateWithLifecycle()

    SkusScreen(
        modifier = modifier,
        skusUiState = skusUiState,
        onSelectSku = onSkuSelect
    )
}

@Composable
fun SkusScreen(
    modifier: Modifier = Modifier,
    skusUiState: SkusUiState = SkusUiState.Loading,
    onSelectSku: (id: Int) -> Unit = {}
) {
    when (skusUiState) {
        SkusUiState.Loading -> ContentLoadingScreen()

        is SkusUiState.Success -> {
            if (skusUiState.isEmpty()) {
                ContentEmpty()
            } else {
                SkuList(
                    modifier = modifier,
                    data = skusUiState.data,
                    onClickSku = onSelectSku
                )
            }
        }
    }
}

@Composable
fun SkuList(
    onClickSku: (id: Int) -> Unit,
    modifier: Modifier = Modifier,
    data: List<Sku> = emptyList()
) {
    LazyColumn(modifier.fillMaxSize()) {
        items(data, key = { it.id }) { item ->
            SkuListItem(sku = item, onClick = { onClickSku(item.id) })
            HorizontalDivider()
        }
    }
}

@Composable
fun SkuListItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    sku: Sku = Sku()
) {
    ListItem(
        modifier = modifier.clickable(
            onClick = {
                onClick()
            }
        ),
        headlineContent = {
            Text(sku.name)
        },
        supportingContent = {
            Text(
                if (sku.type == 1) {
                    stringResource(R.string.tara)
                } else {
                    stringResource(R.string.sku)
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun FieldsScreenPreview(modifier: Modifier = Modifier) {
    HarvestingTheme {
        SkusScreen(modifier = modifier, skusUiState = SkusUiState.Success())
    }
}
