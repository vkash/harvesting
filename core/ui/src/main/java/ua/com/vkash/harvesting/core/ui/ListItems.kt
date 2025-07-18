package ua.com.vkash.harvesting.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ua.com.vkash.harvesting.core.designsystem.icons.AccountHardHat
import ua.com.vkash.harvesting.core.designsystem.icons.FruitGrapes
import ua.com.vkash.harvesting.core.designsystem.icons.LandFields
import ua.com.vkash.harvesting.core.designsystem.icons.LandPlots
import ua.com.vkash.harvesting.core.designsystem.icons.PackageVariant
import ua.com.vkash.harvesting.core.designsystem.theme.HarvestingTheme

@Composable
fun ListItemWorker(
    modifier: Modifier = Modifier,
    header: String = stringResource(R.string.worker),
    name: String = "",
    isEnabled: Boolean = false
) {
    ListItem(
        modifier = modifier,
        headlineContent = {
            Text(text = header)
        },
        supportingContent = {
            Text(
                text = name.ifBlank { stringResource(R.string.scan_barcode) }
            )
        },
        trailingContent = {
            if (isEnabled) {
                Icon(
                    imageVector = Icons.Default.QrCodeScanner,
                    contentDescription = stringResource(R.string.scan_barcode)
                )
            }
        },
        leadingContent = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = header
            )
        }
    )
}

@Composable
fun ListItemBrigadier(
    modifier: Modifier = Modifier,
    header: String = stringResource(R.string.brigadier),
    name: String = "",
    isEnabled: Boolean = false
) {
    ListItem(
        modifier = modifier,
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
        ),
        headlineContent = {
            Text(text = header)
        },
        supportingContent = {
            Text(
                text = name.ifBlank { stringResource(R.string.scan_barcode) }
            )
        },
        trailingContent = {
            if (isEnabled) {
                Icon(
                    imageVector = Icons.Default.QrCodeScanner,
                    contentDescription = stringResource(R.string.scan_barcode)
                )
            }
        },
        leadingContent = {
            Icon(
                imageVector = AccountHardHat,
                contentDescription = header
            )
        }
    )
}

@Composable
fun ListItemField(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { /* No-op */ },
    header: String = stringResource(R.string.field),
    name: String = "",
    isEnabled: Boolean = false
) {
    ListItem(
        modifier = modifier.clickable(isEnabled, onClick = onClick),
        headlineContent = {
            Text(text = header)
        },
        supportingContent = {
            Text(
                text = name.ifBlank { stringResource(R.string.not_specified) }
            )
        },
        leadingContent = {
            Icon(
                imageVector = LandFields,
                contentDescription = header
            )
        },
        trailingContent = {
            if (isEnabled) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowRight,
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
fun ListItemSquare(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { /* No-op */ },
    header: String = stringResource(R.string.square),
    name: String = "",
    isEnabled: Boolean = false
) {
    ListItem(
        modifier = modifier.clickable(isEnabled, onClick = onClick),
        headlineContent = {
            Text(text = header)
        },
        supportingContent = {
            Text(
                text = name.ifBlank { stringResource(R.string.not_specified) }
            )
        },
        trailingContent = {
            if (isEnabled) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowRight,
                    contentDescription = null
                )
            }
        },
        leadingContent = {
            Icon(
                imageVector = LandPlots,
                contentDescription = header
            )
        }
    )
}

@Composable
fun ListItemSku(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { /* No-op */ },
    header: String = stringResource(R.string.sku),
    name: String = "",
    isEnabled: Boolean = false
) {
    ListItem(
        modifier = modifier.clickable(isEnabled, onClick = onClick),
        headlineContent = {
            Text(text = header)
        },
        supportingContent = {
            Text(
                text = name.ifBlank { stringResource(R.string.not_specified) }
            )
        },
        leadingContent = {
            Icon(
                imageVector = FruitGrapes,
                contentDescription = header
            )
        },
        trailingContent = {
            if (isEnabled) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowRight,
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
fun ListItemTara(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    header: String = stringResource(R.string.tara),
    name: String = "",
    isEnabled: Boolean = false
) {
    ListItem(
        modifier = modifier.clickable(isEnabled, onClick = onClick),
        headlineContent = {
            Text(text = header)
        },
        supportingContent = {
            Text(
                text = name.ifBlank { stringResource(R.string.not_specified) }
            )
        },
        trailingContent = {
            if (isEnabled) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowRight,
                    contentDescription = null
                )
            }
        },
        leadingContent = {
            Icon(
                imageVector = PackageVariant,
                contentDescription = header
            )
        }
    )
}

@Composable
fun ListItemWeight(
    modifier: Modifier = Modifier,
    onClick: (Double) -> Unit = { /* No-op */ },
    header: String = stringResource(R.string.weight),
    qty: Double = 0.0,
    isEnabled: Boolean = false
) {
    ListItem(
        modifier = modifier.clickable(isEnabled, onClick = { onClick(qty) }),
        headlineContent = {
            Text(text = header)
        },
        supportingContent = {
            Text(
                text = qty.toString()
            )
        },
        trailingContent = {
            if (isEnabled) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowRight,
                    contentDescription = null
                )
            }
        },
        leadingContent = {
            Icon(
                imageVector = Icons.Default.Scale,
                contentDescription = header
            )
        }
    )
}

@Composable
fun ListItemWorkType(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { /* No-op */ },
    header: String = stringResource(R.string.work_type),
    name: String = "",
    isEnabled: Boolean = false
) {
    ListItem(
        modifier = modifier.clickable(isEnabled, onClick = onClick),
        headlineContent = {
            Text(text = header)
        },
        supportingContent = {
            Text(
                text = name.ifBlank { stringResource(R.string.not_specified) }
            )
        },
        trailingContent = {
            if (isEnabled) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowRight,
                    contentDescription = null
                )
            }
        },
        leadingContent = {
            Icon(
                imageVector = Icons.Default.Agriculture,
                contentDescription = header
            )
        }
    )
}

@Composable
fun ListItemQty(
    modifier: Modifier = Modifier,
    onClick: (Double) -> Unit = { /* No-op */ },
    header: String = stringResource(R.string.qty),
    qty: Double = 0.0,
    isEnabled: Boolean = false
) {
    ListItem(
        modifier = modifier.clickable(isEnabled, onClick = { onClick(qty) }),
        headlineContent = {
            Text(text = header)
        },
        supportingContent = {
            Text(
                text = qty.toString()
            )
        },
        trailingContent = {
            if (isEnabled) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowRight,
                    contentDescription = null
                )
            }
        },
        leadingContent = {
            Icon(
                imageVector = Icons.Default.Calculate,
                contentDescription = header
            )
        }
    )
}

@Composable
fun ListItemQty(
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit = { /* No-op */ },
    header: String = stringResource(R.string.qty),
    qty: Int = 0,
    isEnabled: Boolean = false
) {
    ListItem(
        modifier = modifier.clickable(isEnabled, onClick = { onClick(qty) }),
        headlineContent = {
            Text(text = header)
        },
        supportingContent = {
            Text(
                text = qty.toString()
            )
        },
        trailingContent = {
            if (isEnabled) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowRight,
                    contentDescription = null
                )
            }
        },
        leadingContent = {
            Icon(
                imageVector = Icons.Default.Calculate,
                contentDescription = header
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun ListItemWorkerPreview(modifier: Modifier = Modifier) {
    HarvestingTheme {
        Column(modifier = modifier) {
            ListItemWorker()
            ListItemBrigadier()
            ListItemField(isEnabled = true)
            ListItemSquare(isEnabled = true)
            ListItemSku(isEnabled = true)
            ListItemTara(isEnabled = true)
            ListItemWeight(isEnabled = true)
            ListItemWorkType(isEnabled = true)
            ListItemQty(qty = 0.0, isEnabled = true)
            ListItemQty(qty = 0, isEnabled = true)
        }
    }
}
