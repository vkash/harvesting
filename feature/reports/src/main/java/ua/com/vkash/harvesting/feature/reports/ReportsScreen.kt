package ua.com.vkash.harvesting.feature.reports

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Analytics
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
import ua.com.vkash.harvesting.core.designsystem.theme.HarvestingTheme
import ua.com.vkash.harvesting.core.model.data.Report
import ua.com.vkash.harvesting.core.model.data.ReportId
import ua.com.vkash.harvesting.core.ui.ContentEmpty
import ua.com.vkash.harvesting.core.ui.ContentLoadingScreen

@Composable
fun ReportsRoute(
    modifier: Modifier = Modifier,
    onReportClick: (id: String) -> Unit = {},
    viewModel: ReportsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ReportsScreen(
        modifier = modifier,
        reportsUiState = uiState,
        onReportClick = onReportClick
    )
}

@Composable
fun ReportsScreen(
    modifier: Modifier = Modifier,
    reportsUiState: ReportsUiState = ReportsUiState.Loading,
    onReportClick: (id: String) -> Unit = {}
) {
    when (reportsUiState) {
        ReportsUiState.Loading -> ContentLoadingScreen()

        is ReportsUiState.Success -> if (reportsUiState.isEmpty()) {
            ContentEmpty()
        } else {
            ReportList(
                modifier = modifier,
                data = reportsUiState.data,
                onClickReport = onReportClick
            )
        }
    }
}

@Composable
fun ReportList(
    modifier: Modifier = Modifier,
    onClickReport: (id: String) -> Unit = {},
    data: List<Report> = emptyList()
) {
    LazyColumn(modifier.fillMaxSize()) {
        items(data, key = { it.reportId }) { item ->
            ReportListItem(report = item, onClick = { onClickReport(item.reportId.id) })
            HorizontalDivider()
        }
    }
}

@Composable
fun ReportListItem(
    report: Report,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ListItem(
        modifier = modifier.clickable(
            onClick = onClick
        ),
        headlineContent = {
            Text(
                when (report.reportId) {
                    ReportId.HarvestToday -> stringResource(R.string.harvest_today)
                    ReportId.HarvestByBrigade -> stringResource(R.string.report_by_brigade)
                }
            )
        },
        leadingContent = {
            Icon(
                Icons.Outlined.Analytics,
                contentDescription = null
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun ReportsScreenPreview(modifier: Modifier = Modifier) {
    HarvestingTheme {
        ReportsScreen(modifier, ReportsUiState.Success())
    }
}
