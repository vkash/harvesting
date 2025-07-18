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

val AccountHardHat: ImageVector
    get() {
        if (accountHardHat != null) {
            return accountHardHat!!
        }
        accountHardHat =
            Builder(
                name = "AccountHardHat",
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
                        moveTo(12.0f, 15.0f)
                        curveToRelative(-4.42f, 0.0f, -8.0f, 1.79f, -8.0f, 4.0f)
                        verticalLineToRelative(2.0f)
                        horizontalLineToRelative(16.0f)
                        verticalLineToRelative(-2.0f)
                        curveToRelative(0.0f, -2.21f, -3.58f, -4.0f, -8.0f, -4.0f)
                        moveTo(8.0f, 9.0f)
                        arcToRelative(
                            4.0f,
                            4.0f,
                            0.0f,
                            isMoreThanHalf = false,
                            isPositiveArc = false,
                            dx1 = 4.0f,
                            dy1 = 4.0f
                        )
                        arcToRelative(
                            4.0f,
                            4.0f,
                            0.0f,
                            isMoreThanHalf = false,
                            isPositiveArc = false,
                            dx1 = 4.0f,
                            dy1 = -4.0f
                        )
                        moveToRelative(-4.5f, -7.0f)
                        curveToRelative(-0.3f, 0.0f, -0.5f, 0.21f, -0.5f, 0.5f)
                        verticalLineToRelative(3.0f)
                        horizontalLineToRelative(-1.0f)
                        verticalLineTo(3.0f)
                        reflectiveCurveToRelative(-2.25f, 0.86f, -2.25f, 3.75f)
                        curveToRelative(0.0f, 0.0f, -0.75f, 0.14f, -0.75f, 1.25f)
                        horizontalLineToRelative(10.0f)
                        curveToRelative(-0.05f, -1.11f, -0.75f, -1.25f, -0.75f, -1.25f)
                        curveTo(16.25f, 3.86f, 14.0f, 3.0f, 14.0f, 3.0f)
                        verticalLineToRelative(2.5f)
                        horizontalLineToRelative(-1.0f)
                        verticalLineToRelative(-3.0f)
                        curveToRelative(0.0f, -0.29f, -0.19f, -0.5f, -0.5f, -0.5f)
                        close()
                    }
                }
                .build()
        return accountHardHat!!
    }

private var accountHardHat: ImageVector? = null
