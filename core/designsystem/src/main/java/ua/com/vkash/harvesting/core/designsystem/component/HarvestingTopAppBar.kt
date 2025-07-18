package ua.com.vkash.harvesting.core.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ua.com.vkash.harvesting.core.designsystem.R
import ua.com.vkash.harvesting.core.designsystem.theme.HarvestingTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HarvestingTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    subtitle: String = "",
    isTopDestination: Boolean = false,
    onNavigationIconClick: () -> Unit = {},
    onActionClick: () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier.shadow(elevation = 8.dp),
        title = {
            Column {
                Text(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                AnimatedVisibility(subtitle.isNotBlank()) {
                    Text(
                        text = subtitle,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        },
        navigationIcon = {
            if (!isTopDestination) {
                IconButton(onClick = onNavigationIconClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        },
        actions = {
            if (isTopDestination) {
                IconButton(onClick = onActionClick) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = stringResource(R.string.settings)
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun HarvestingTopAppBarPreview(modifier: Modifier = Modifier) {
    HarvestingTheme {
        HarvestingTopAppBar(
            modifier = modifier,
            isTopDestination = true,
            title = stringResource(R.string.app_name),
            subtitle = stringResource(R.string.agro_farm)
        )
    }
}
