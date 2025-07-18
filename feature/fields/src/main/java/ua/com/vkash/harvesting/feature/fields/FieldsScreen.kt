package ua.com.vkash.harvesting.feature.fields

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
import ua.com.vkash.harvesting.core.model.data.Field
import ua.com.vkash.harvesting.core.ui.ContentEmpty
import ua.com.vkash.harvesting.core.ui.ContentLoadingScreen

@Composable
fun FieldsRoute(
    modifier: Modifier = Modifier,
    viewModel: FieldsViewModel = hiltViewModel(),
    onFieldSelect: (id: Int) -> Unit = {}
) {
    val fieldsUiState by viewModel.uiState.collectAsStateWithLifecycle()

    FieldsScreen(
        modifier = modifier,
        fieldsUiState = fieldsUiState,
        onFieldSelect = onFieldSelect
    )
}

@Composable
fun FieldsScreen(
    modifier: Modifier = Modifier,
    fieldsUiState: FieldsUiState = FieldsUiState.Loading,
    onFieldSelect: (id: Int) -> Unit = {}
) {
    when (fieldsUiState) {
        FieldsUiState.Loading -> ContentLoadingScreen()

        is FieldsUiState.Success -> {
            if (fieldsUiState.isEmpty()) {
                ContentEmpty()
            } else {
                FieldList(
                    modifier = modifier,
                    data = fieldsUiState.data,
                    onClickItem = onFieldSelect
                )
            }
        }
    }
}

@Composable
fun FieldList(
    modifier: Modifier = Modifier,
    onClickItem: (id: Int) -> Unit = {},
    data: List<Field> = emptyList()
) {
    LazyColumn(modifier.fillMaxSize()) {
        items(data, key = { it.id }) { item ->
            FieldListItem(name = item.name, onClick = { onClickItem(item.id) })
            HorizontalDivider()
        }
    }
}

@Composable
fun FieldListItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    name: String = ""
) {
    ListItem(modifier = modifier.clickable(onClick = onClick), headlineContent = {
        Text(name)
    })
}

@Preview(showBackground = true)
@Composable
private fun FieldsScreenPreview(modifier: Modifier = Modifier) {
    HarvestingTheme {
        FieldsScreen(modifier = modifier, fieldsUiState = FieldsUiState.Success())
    }
}
