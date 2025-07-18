package ua.com.vkash.harvesting.feature.reports

import android.os.Build
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ua.com.vkash.harvesting.core.ui.ContentLoadingScreen

@Composable
fun ReportsRoute(modifier: Modifier = Modifier, viewModel: ReportViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { viewModel.listenForBarcodes() }

    ReportScreen(
        modifier = modifier,
        reportUiState = uiState
    )
}

@Composable
fun ReportScreen(
    modifier: Modifier = Modifier,
    reportUiState: ReportUiState = ReportUiState.Loading
) {
    when (reportUiState) {
        ReportUiState.Loading -> ContentLoadingScreen()
        is ReportUiState.Success -> WebScreen(modifier = modifier, html = reportUiState.data)
    }
}

@Composable
fun WebScreen(modifier: Modifier = Modifier, html: String = "") {
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = {
            WebView(it).apply {
                val settings = getSettings()
                settings.builtInZoomControls = true
                settings.cacheMode = WebSettings.LOAD_NO_CACHE
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    settings.isAlgorithmicDarkeningAllowed = true
                }
            }
        },
        update = { webView ->
            webView.loadData(html, "text/html; charset=utf-8", "base64")
        },
        onRelease = { webView ->
            webView.destroy()
        }
    )
}
