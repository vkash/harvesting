package ua.com.vkash.harvesting.feature.appmenu

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import ua.com.vkash.harvesting.core.designsystem.theme.HarvestingTheme
import ua.com.vkash.harvesting.core.ui.ContentEmpty
import ua.com.vkash.harvesting.core.ui.ContentLoadingScreen

@Composable
fun MainRoute(
    modifier: Modifier = Modifier,
    onCardClick: (MainCard) -> Unit = {},
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    val mainUiState by viewModel.mainUiState.collectAsStateWithLifecycle()

    MainScreen(
        modifier = modifier,
        mainUiState = mainUiState,
        onCardClick = onCardClick
    )
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onCardClick: (MainCard) -> Unit = {},
    mainUiState: MainUiState = MainUiState.Loading
) {
    when (mainUiState) {
        MainUiState.Loading -> ContentLoadingScreen()

        is MainUiState.Success -> {
            if (mainUiState.isEmpty()) {
                ContentEmpty(text = stringResource(R.string.goto_settings_and_scan_badge))
            } else {
                MainMenu(
                    modifier = modifier,
                    data = mainUiState.data,
                    onItemClick = { card -> onCardClick(card) }
                )
            }
        }
    }
}

@Composable
fun MainMenu(
    modifier: Modifier = Modifier,
    onItemClick: (MainCard) -> Unit = {},
    data: List<MainCard> = emptyList(),
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
) {
    val columns = if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT) 1 else 2
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(columns),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(count = data.size) { position ->
            data[position].let { item ->
                MainListItem(
                    titleId = item.title,
                    iconId = item.image,
                    onClick = { onItemClick(item) }
                )
            }
        }
    }
}

@Composable
fun MainListItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    @StringRes titleId: Int = 0,
    @DrawableRes iconId: Int = 0
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.elevatedCardElevation(),
        onClick = onClick
    ) {
        Image(
            painter = painterResource(id = iconId),
            contentScale = ContentScale.FillWidth,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp),
            colorFilter = ColorFilter.colorMatrix(
                ColorMatrix().apply {
                    setToSaturation(if (isSystemInDarkTheme()) 0.25f else 1f)
                }
            )
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 8.dp),
            text = stringResource(id = titleId),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MainListPreview(modifier: Modifier = Modifier) {
    HarvestingTheme {
        MainScreen(
            modifier = modifier,
            mainUiState = MainUiState.Success()
        )
    }
}
