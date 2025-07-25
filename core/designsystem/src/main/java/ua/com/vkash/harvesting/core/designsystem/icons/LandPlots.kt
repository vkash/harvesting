package ua.com.vkash.harvesting.core.designsystem.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val LandPlots: ImageVector
    get() {
        if (landPlots != null) {
            return landPlots!!
        }
        landPlots =
            Builder(
                name = "LandPlots",
                defaultWidth = 24.0.dp,
                defaultHeight = 24.0.dp,
                viewportWidth = 24.0f,
                viewportHeight = 24.0f
            )
                .apply {
                    path(
                        fill = SolidColor(Color(0xFF000000)),
                        stroke = null,
                        strokeLineWidth = 0.0f,
                        strokeLineCap = Butt,
                        strokeLineJoin = Miter,
                        strokeLineMiter = 4.0f,
                        pathFillType = NonZero
                    ) {
                        moveTo(20.0f, 2.0f)
                        lineTo(4.0f, 2.0f)
                        curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                        verticalLineToRelative(16.0f)
                        curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                        horizontalLineToRelative(16.0f)
                        curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                        lineTo(22.0f, 4.0f)
                        curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                        moveTo(4.0f, 4.0f)
                        horizontalLineToRelative(4.0f)
                        verticalLineToRelative(10.0f)
                        lineTo(4.0f, 14.0f)
                        close()
                        moveTo(4.0f, 20.0f)
                        verticalLineToRelative(-4.0f)
                        horizontalLineToRelative(4.0f)
                        verticalLineToRelative(4.0f)
                        close()
                        moveTo(20.0f, 20.0f)
                        lineTo(10.0f, 20.0f)
                        lineTo(10.0f, 10.0f)
                        horizontalLineToRelative(10.0f)
                        close()
                        moveTo(20.0f, 8.0f)
                        lineTo(10.0f, 8.0f)
                        lineTo(10.0f, 4.0f)
                        horizontalLineToRelative(10.0f)
                        close()
                    }
                }
                .build()
        return landPlots!!
    }

private var landPlots: ImageVector? = null
