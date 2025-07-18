package ua.com.vkash.harvesting.feature.settings

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun SettingsGroup(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(title)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            shadowElevation = 4.dp,
            tonalElevation = 4.dp
        ) {
            Column {
                content()
            }
        }
    }
}

@Composable
fun SettingsTextItem(
    @StringRes title: Int,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    summary: String? = null,
    icon2: ImageVector? = null,
    onClick: () -> Unit = { },
    enabled: Boolean = true
) {
    ListItem(
        modifier = modifier.clickable(enabled, onClick = onClick),
        headlineContent = {
            Text(
                text = stringResource(title)
            )
        },
        supportingContent = {
            summary?.let {
                Text(
                    text = it
                )
            }
        },
        leadingContent = {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
        },
        trailingContent = {
            icon2?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null
                )
            }
        }
    )
}
