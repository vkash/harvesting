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

val FruitGrapes: ImageVector
    get() {
        if (fruitGrapes != null) {
            return fruitGrapes!!
        }
        fruitGrapes =
            Builder(
                name = "FruitGrapes",
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
                        moveTo(14.0f, 12.0f)
                        curveToRelative(0.0f, 1.1f, -0.9f, 2.0f, -2.0f, 2.0f)
                        reflectiveCurveToRelative(-2.0f, -0.9f, -2.0f, -2.0f)
                        reflectiveCurveToRelative(0.9f, -2.0f, 2.0f, -2.0f)
                        reflectiveCurveToRelative(2.0f, 0.9f, 2.0f, 2.0f)
                        moveToRelative(-7.0f, -2.0f)
                        curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                        reflectiveCurveToRelative(0.9f, 2.0f, 2.0f, 2.0f)
                        reflectiveCurveToRelative(2.0f, -0.9f, 2.0f, -2.0f)
                        reflectiveCurveToRelative(-0.9f, -2.0f, -2.0f, -2.0f)
                        moveToRelative(10.0f, 0.0f)
                        curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                        reflectiveCurveToRelative(0.9f, 2.0f, 2.0f, 2.0f)
                        reflectiveCurveToRelative(2.0f, -0.9f, 2.0f, -2.0f)
                        reflectiveCurveToRelative(-0.9f, -2.0f, -2.0f, -2.0f)
                        moveToRelative(-2.5f, -4.0f)
                        curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                        reflectiveCurveToRelative(0.9f, 2.0f, 2.0f, 2.0f)
                        reflectiveCurveToRelative(2.0f, -0.9f, 2.0f, -2.0f)
                        reflectiveCurveToRelative(-0.9f, -2.0f, -2.0f, -2.0f)
                        moveToRelative(-5.0f, 0.0f)
                        curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                        reflectiveCurveToRelative(0.9f, 2.0f, 2.0f, 2.0f)
                        reflectiveCurveToRelative(2.0f, -0.9f, 2.0f, -2.0f)
                        reflectiveCurveToRelative(-0.9f, -2.0f, -2.0f, -2.0f)
                        moveToRelative(5.0f, 8.0f)
                        curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                        reflectiveCurveToRelative(0.9f, 2.0f, 2.0f, 2.0f)
                        reflectiveCurveToRelative(2.0f, -0.9f, 2.0f, -2.0f)
                        reflectiveCurveToRelative(-0.9f, -2.0f, -2.0f, -2.0f)
                        moveToRelative(-5.0f, 0.0f)
                        curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                        reflectiveCurveToRelative(0.9f, 2.0f, 2.0f, 2.0f)
                        reflectiveCurveToRelative(2.0f, -0.9f, 2.0f, -2.0f)
                        reflectiveCurveToRelative(-0.9f, -2.0f, -2.0f, -2.0f)
                        moveToRelative(2.5f, 4.0f)
                        curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                        reflectiveCurveToRelative(0.9f, 2.0f, 2.0f, 2.0f)
                        reflectiveCurveToRelative(2.0f, -0.9f, 2.0f, -2.0f)
                        reflectiveCurveToRelative(-0.9f, -2.0f, -2.0f, -2.0f)
                        moveToRelative(2.4f, -15.8f)
                        lineTo(13.6f, 1.0f)
                        curveToRelative(-2.2f, 1.0f, -2.4f, 4.6f, -2.4f, 5.0f)
                        horizontalLineToRelative(1.5f)
                        curveToRelative(0.1f, -0.8f, 0.4f, -3.3f, 1.7f, -3.8f)
                    }
                }
                .build()
        return fruitGrapes!!
    }

private var fruitGrapes: ImageVector? = null
