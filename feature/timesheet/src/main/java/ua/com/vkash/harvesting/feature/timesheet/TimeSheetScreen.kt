package ua.com.vkash.harvesting.feature.timesheet

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import ua.com.vkash.harvesting.core.designsystem.theme.HarvestingTheme
import ua.com.vkash.harvesting.core.model.data.TimeSheetDetail
import ua.com.vkash.harvesting.core.ui.ContentEmpty
import ua.com.vkash.harvesting.core.ui.ContentLoadingScreen

@Composable
fun TimeSheetRoute(
    modifier: Modifier = Modifier,
    viewModel: TimeSheetViewModel = hiltViewModel()
) {
    val timeSheetUiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { viewModel.listenForBarcodes() }

    TimeSheetScreen(
        modifier = modifier,
        timeSheetUiState = timeSheetUiState
    )
}

@Composable
fun TimeSheetScreen(
    modifier: Modifier = Modifier,
    timeSheetUiState: TimeSheetUiState = TimeSheetUiState.Loading
) {
    when (timeSheetUiState) {
        TimeSheetUiState.Loading -> ContentLoadingScreen()

        is TimeSheetUiState.Success -> {
            if (timeSheetUiState.isEmpty()) {
                ContentEmpty()
            } else {
                TimeSheetList(
                    modifier = modifier,
                    data = timeSheetUiState.data
                )
            }
        }
    }
}

@Composable
fun TimeSheetList(
    modifier: Modifier = Modifier,
    data: List<TimeSheetDetail> = emptyList()
) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    LazyColumn(modifier = modifier.fillMaxSize(), state = listState) {
        items(data, key = { it.id }) { item ->
            TimeSheetListItem(timeSheetDetail = item)
            HorizontalDivider()
        }
        scope.launch {
            listState.animateScrollToItem(0)
        }
    }
}

@Composable
fun TimeSheetListItem(
    timeSheetDetail: TimeSheetDetail,
    modifier: Modifier = Modifier
) {
    ListItem(
        modifier = modifier,
        headlineContent = {
            Text(timeSheetDetail.workerName)
        },
        supportingContent = {
            Text(timeSheetDetail.fieldName)
        },
        leadingContent = {
            Icon(
                imageVector = if (timeSheetDetail.eventStart) {
                    Icons.AutoMirrored.Filled.ArrowForward
                } else {
                    Icons.AutoMirrored.Filled.ArrowBack
                },
                tint = if (timeSheetDetail.eventStart) Color.Green else Color.Red,
                contentDescription = null
            )
        },
        overlineContent = {
            Text(
                text = stringResource(
                    R.string.date_time,
                    timeSheetDetail.eventTime
                )
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun TimeSheetScreenPreview(modifier: Modifier = Modifier) {
    HarvestingTheme {
        TimeSheetScreen(
            modifier = modifier,
            timeSheetUiState = TimeSheetUiState.Success()
        )
    }
}
