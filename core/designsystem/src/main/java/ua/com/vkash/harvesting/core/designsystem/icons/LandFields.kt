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

val LandFields: ImageVector
    get() {
        if (landFields != null) {
            return landFields!!
        }
        landFields =
            Builder(
                name = "LandFields",
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
                        moveToRelative(-4.7f, 2.0f)
                        curveTo(14.5f, 5.1f, 14.0f, 6.5f, 14.0f, 8.0f)
                        horizontalLineToRelative(-4.0f)
                        curveToRelative(0.0f, -1.6f, 0.8f, -3.1f, 2.0f, -4.0f)
                        close()
                        moveTo(14.0f, 14.0f)
                        horizontalLineToRelative(-4.0f)
                        curveToRelative(0.0f, -1.5f, -0.5f, -2.9f, -1.3f, -4.0f)
                        lineTo(12.0f, 10.0f)
                        curveToRelative(1.2f, 0.9f, 2.0f, 2.4f, 2.0f, 4.0f)
                        moveTo(4.0f, 4.0f)
                        horizontalLineToRelative(5.3f)
                        curveTo(8.5f, 5.1f, 8.0f, 6.5f, 8.0f, 8.0f)
                        lineTo(4.0f, 8.0f)
                        close()
                        moveTo(4.0f, 10.0f)
                        horizontalLineToRelative(2.0f)
                        curveToRelative(1.2f, 0.9f, 2.0f, 2.3f, 2.0f, 4.0f)
                        lineTo(4.0f, 14.0f)
                        close()
                        moveTo(4.0f, 20.0f)
                        verticalLineToRelative(-4.0f)
                        horizontalLineToRelative(5.3f)
                        curveTo(8.5f, 17.1f, 8.0f, 18.5f, 8.0f, 20.0f)
                        close()
                        moveTo(10.0f, 20.0f)
                        curveToRelative(0.0f, -1.6f, 0.8f, -3.1f, 2.0f, -4.0f)
                        horizontalLineToRelative(3.3f)
                        curveToRelative(-0.8f, 1.1f, -1.3f, 2.5f, -1.3f, 4.0f)
                        close()
                        moveTo(20.0f, 20.0f)
                        horizontalLineToRelative(-4.0f)
                        curveToRelative(0.0f, -1.6f, 0.8f, -3.1f, 2.0f, -4.0f)
                        horizontalLineToRelative(2.0f)
                        close()
                        moveTo(20.0f, 14.0f)
                        horizontalLineToRelative(-4.0f)
                        curveToRelative(0.0f, -1.5f, -0.5f, -2.9f, -1.3f, -4.0f)
                        lineTo(20.0f, 10.0f)
                        close()
                        moveTo(20.0f, 8.0f)
                        horizontalLineToRelative(-4.0f)
                        curveToRelative(0.0f, -1.6f, 0.8f, -3.1f, 2.0f, -4.0f)
                        horizontalLineToRelative(2.0f)
                        close()
                    }
                }
                .build()
        return landFields!!
    }

private var landFields: ImageVector? = null
