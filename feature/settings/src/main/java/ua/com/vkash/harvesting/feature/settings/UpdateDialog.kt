package ua.com.vkash.harvesting.feature.settings

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    syncUiState: SyncUiState = SyncUiState.Ready
) {
    val (progress, message) = when (syncUiState) {
        SyncUiState.Done -> 1f to stringResource(R.string.loading_upk_complete)
        is SyncUiState.Error -> 0f to syncUiState.message
        is SyncUiState.Process -> syncUiState.progress to syncUiState.message
        SyncUiState.Ready -> 0f to stringResource(R.string.preparation)
    }
    val dismiss = when (syncUiState) {
        is SyncUiState.Error -> true
        SyncUiState.Done -> true
        else -> false
    }

    val animatedProgress by animateFloatAsState(targetValue = progress)

    BasicAlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = dismiss,
            dismissOnClickOutside = dismiss
        )
    ) {
        Surface(
            modifier = modifier,
            shape = AlertDialogDefaults.shape,
            color = AlertDialogDefaults.containerColor,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(64.dp),
                    gapSize = 8.dp,
                    strokeWidth = 8.dp,
                    progress = { animatedProgress }
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = message,
                    maxLines = 1,
                    color = AlertDialogDefaults.textContentColor,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
