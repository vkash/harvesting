package ua.com.priberry.harvesting.feature.calculator

import android.content.res.Configuration
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.text.DecimalFormatSymbols
import ua.com.vkash.harvesting.core.designsystem.theme.HarvestingTheme

@Composable
fun CalculatorRoute(
    modifier: Modifier = Modifier,
    onConfirmInput: (String, Double) -> Unit = { k, v -> },
    viewModel: CalculatorScreenViewModel = hiltViewModel()
) {
    val displayText by viewModel.displayText.collectAsStateWithLifecycle()

    CalculatorScreen(
        modifier = modifier,
        displayText = displayText,
        isFractional = viewModel.isFractional,
        onDigit = viewModel::inputDigit,
        onDot = viewModel::inputDot,
        onBackspace = viewModel::backspace,
        onClear = viewModel::clear,
        onConfirmInput = { onConfirmInput(viewModel.inputKey, viewModel.value) }
    )
}

@Composable
fun CalculatorScreen(
    modifier: Modifier = Modifier,
    onDigit: (Char) -> Unit = {},
    onDot: () -> Unit = {},
    onBackspace: () -> Unit = {},
    onClear: () -> Unit = {},
    onConfirmInput: () -> Unit = {},
    displayText: String = "",
    isFractional: Boolean = true
) {
    var dpSize by remember { mutableStateOf(DpSize.Zero) }
    val density = LocalDensity.current

    Surface(modifier = modifier) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DisplayItem(
                    modifier = Modifier.weight(1f),
                    value = displayText
                )
                KeyItem(
                    modifier = Modifier.size(dpSize),
                    key = "⌫",
                    onClick = onBackspace,
                    onLongClick = onClear
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                KeyItem(
                    modifier = Modifier
                        .weight(1f)
                        .onSizeChanged {
                            dpSize = density.run {
                                DpSize(it.width.toDp(), it.height.toDp())
                            }
                        },
                    key = "7",
                    onClick = { onDigit('7') }
                )
                KeyItem(modifier = Modifier.weight(1f), key = "8", onClick = { onDigit('8') })
                KeyItem(modifier = Modifier.weight(1f), key = "9", onClick = { onDigit('9') })
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                KeyItem(modifier = Modifier.weight(1f), key = "4", onClick = { onDigit('4') })
                KeyItem(modifier = Modifier.weight(1f), key = "5", onClick = { onDigit('5') })
                KeyItem(modifier = Modifier.weight(1f), key = "6", onClick = { onDigit('6') })
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                KeyItem(modifier = Modifier.weight(1f), key = "1", onClick = { onDigit('1') })
                KeyItem(modifier = Modifier.weight(1f), key = "2", onClick = { onDigit('2') })
                KeyItem(modifier = Modifier.weight(1f), key = "3", onClick = { onDigit('3') })
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                KeyItem(modifier = Modifier.weight(1f), key = "0", onClick = { onDigit('0') })
                KeyItem(
                    modifier = Modifier.weight(1f),
                    key = DecimalFormatSymbols.getInstance().decimalSeparator.toString(),
                    isEnabled = isFractional,
                    onClick = onDot
                )
                KeyItem(
                    modifier = Modifier.weight(1f),
                    key = stringResource(R.string.ok),
                    onClick = onConfirmInput
                )
            }
        }
    }
}

@Composable
fun KeyItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
    key: String = "",
    isEnabled: Boolean = true
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(8.dp)
            .clip(CircleShape)
            .combinedClickable(enabled = isEnabled, onClick = onClick, onLongClick = onLongClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = key,
            fontFamily = FontFamily.Monospace,
            fontSize = 36.sp
        )
    }
}

@Composable
fun DisplayItem(modifier: Modifier = Modifier, value: String = "0") {
    val textStyle = MaterialTheme.typography.displayLarge.copy(fontSize = 48.sp)
    var scaledTextStyle by remember { mutableStateOf(textStyle) }
    var readyToDraw by remember { mutableStateOf(false) }

    Text(
        modifier = modifier.drawWithContent {
            if (readyToDraw) drawContent()
        },
        text = value,
        maxLines = 1,
        softWrap = false,
        style = scaledTextStyle,
        textAlign = TextAlign.End,
        onTextLayout = { textLayoutResult ->
            if (textLayoutResult.hasVisualOverflow) {
                scaledTextStyle =
                    scaledTextStyle.copy(fontSize = scaledTextStyle.fontSize * 0.9)
            } else {
                readyToDraw = true
            }
        }
    )
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CalculatorScreenDarkPreview() {
    HarvestingTheme {
        CalculatorScreen()
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun CalculatorScreenPreview() {
    HarvestingTheme {
        CalculatorScreen()
    }
}
