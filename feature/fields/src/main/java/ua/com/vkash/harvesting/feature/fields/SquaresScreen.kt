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
import ua.com.vkash.harvesting.core.model.data.Square
import ua.com.vkash.harvesting.core.ui.ContentEmpty
import ua.com.vkash.harvesting.core.ui.ContentLoadingScreen

@Composable
fun SquaresRoute(
    modifier: Modifier = Modifier,
    viewModel: SquaresScreenViewModel = hiltViewModel(),
    onSelectSquare: (id: Int) -> Unit = {}
) {
    val squaresUiState by viewModel.uiState.collectAsStateWithLifecycle()

    SquaresScreen(
        modifier = modifier,
        squaresUiState = squaresUiState,
        onSelectSquare = onSelectSquare
    )
}

@Composable
fun SquaresScreen(
    modifier: Modifier = Modifier,
    squaresUiState: SquaresUiState = SquaresUiState.Loading,
    onSelectSquare: (id: Int) -> Unit = {}
) {
    when (squaresUiState) {
        SquaresUiState.Loading -> ContentLoadingScreen()

        is SquaresUiState.Success -> {
            if (squaresUiState.isEmpty()) {
                ContentEmpty()
            } else {
                SquareList(
                    modifier = modifier,
                    data = squaresUiState.data,
                    onClickItem = {
                        onSelectSquare(it)
                    }
                )
            }
        }
    }
}

@Composable
fun SquareList(
    onClickItem: (id: Int) -> Unit,
    modifier: Modifier = Modifier,
    data: List<Square> = emptyList()
) {
    LazyColumn(modifier.fillMaxSize()) {
        items(data, key = { it.id }) { item ->
            SquareListItem(name = item.name, onClick = { onClickItem(item.id) })
            HorizontalDivider()
        }
    }
}

@Composable
fun SquareListItem(
    modifier: Modifier = Modifier,
    name: String = "",
    onClick: () -> Unit = {}
) {
    ListItem(
        modifier = modifier.clickable(
            onClick = {
                onClick()
            }
        ),
        headlineContent = {
            Text(name)
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun SquareScreenPreview(modifier: Modifier = Modifier) {
    HarvestingTheme {
        SquaresScreen(modifier = modifier, squaresUiState = SquaresUiState.Success())
    }
}
