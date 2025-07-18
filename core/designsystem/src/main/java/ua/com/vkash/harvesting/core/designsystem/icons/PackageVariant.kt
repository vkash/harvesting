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

val PackageVariant: ImageVector
    get() {
        if (packageVariant != null) {
            return packageVariant!!
        }
        packageVariant =
            Builder(
                name = "PackageVariant",
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
                        moveTo(2.0f, 10.96f)
                        arcToRelative(
                            0.985f,
                            0.985f,
                            0.0f,
                            isMoreThanHalf = false,
                            isPositiveArc = true,
                            dx1 = -0.37f,
                            dy1 = -1.37f
                        )
                        lineTo(3.13f, 7.0f)
                        curveToRelative(0.11f, -0.2f, 0.28f, -0.34f, 0.47f, -0.42f)
                        lineToRelative(7.83f, -4.4f)
                        curveToRelative(0.16f, -0.12f, 0.36f, -0.18f, 0.57f, -0.18f)
                        reflectiveCurveToRelative(0.41f, 0.06f, 0.57f, 0.18f)
                        lineToRelative(7.9f, 4.44f)
                        curveToRelative(0.19f, 0.1f, 0.35f, 0.26f, 0.44f, 0.46f)
                        lineToRelative(1.45f, 2.52f)
                        curveToRelative(0.28f, 0.48f, 0.11f, 1.09f, -0.36f, 1.36f)
                        lineToRelative(-1.0f, 0.58f)
                        verticalLineToRelative(4.96f)
                        curveToRelative(0.0f, 0.38f, -0.21f, 0.71f, -0.53f, 0.88f)
                        lineToRelative(-7.9f, 4.44f)
                        curveToRelative(-0.16f, 0.12f, -0.36f, 0.18f, -0.57f, 0.18f)
                        reflectiveCurveToRelative(-0.41f, -0.06f, -0.57f, -0.18f)
                        lineToRelative(-7.9f, -4.44f)
                        arcTo(
                            0.99f,
                            0.99f,
                            0.0f,
                            isMoreThanHalf = false,
                            isPositiveArc = true,
                            x1 = 3.0f,
                            y1 = 16.5f
                        )
                        verticalLineToRelative(-5.54f)
                        curveToRelative(-0.3f, 0.17f, -0.68f, 0.18f, -1.0f, 0.0f)
                        moveToRelative(10.0f, -6.81f)
                        verticalLineToRelative(6.7f)
                        lineToRelative(5.96f, -3.35f)
                        close()
                        moveTo(5.0f, 15.91f)
                        lineToRelative(6.0f, 3.38f)
                        verticalLineToRelative(-6.71f)
                        lineTo(5.0f, 9.21f)
                        close()
                        moveTo(19.0f, 15.91f)
                        verticalLineToRelative(-3.22f)
                        lineToRelative(-5.0f, 2.9f)
                        curveToRelative(-0.33f, 0.18f, -0.7f, 0.17f, -1.0f, 0.01f)
                        verticalLineToRelative(3.69f)
                        close()
                        moveTo(13.85f, 13.36f)
                        lineTo(20.13f, 9.73f)
                        lineTo(19.55f, 8.72f)
                        lineTo(13.27f, 12.35f)
                        close()
                    }
                }
                .build()
        return packageVariant!!
    }

private var packageVariant: ImageVector? = null
