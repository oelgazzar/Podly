package com.example.test

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Suppress("CheckReturnValue")
public val podcasts: ImageVector
  get() {
    if (_podcasts != null) {
      return _podcasts!!
    }
    _podcasts =
      ImageVector.Builder(
          name = "podcasts",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(
            fill = SolidColor(Color.Black),
            fillAlpha = 1f,
            stroke = null,
            strokeAlpha = 1f,
            strokeLineWidth = 1f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Bevel,
            strokeLineMiter = 1f,
            pathFillType = PathFillType.Companion.NonZero,
          ) {
            moveTo(11f, 22f)
            verticalLineTo(13.73f)
            quadTo(10.55f, 13.45f, 10.28f, 13.01f)
            reflectiveQuadTo(10f, 12f)
            quadToRelative(0f, -0.83f, 0.59f, -1.41f)
            reflectiveQuadTo(12f, 10f)
            reflectiveQuadToRelative(1.41f, 0.59f)
            quadTo(14f, 11.18f, 14f, 12f)
            quadToRelative(0f, 0.57f, -0.27f, 1.02f)
            quadTo(13.45f, 13.48f, 13f, 13.73f)
            verticalLineTo(22f)
            horizontalLineTo(11f)
            close()
            moveTo(5.1f, 19.25f)
            quadTo(3.68f, 17.88f, 2.84f, 16.01f)
            reflectiveQuadTo(2f, 12f)
            quadTo(2f, 9.92f, 2.79f, 8.1f)
            quadTo(3.58f, 6.27f, 4.93f, 4.93f)
            quadTo(6.28f, 3.57f, 8.1f, 2.79f)
            quadTo(9.93f, 2f, 12f, 2f)
            reflectiveQuadToRelative(3.9f, 0.79f)
            reflectiveQuadToRelative(3.17f, 2.14f)
            quadToRelative(1.35f, 1.35f, 2.14f, 3.17f)
            quadTo(22f, 9.92f, 22f, 12f)
            quadToRelative(0f, 2.15f, -0.84f, 4.02f)
            reflectiveQuadTo(18.9f, 19.25f)
            lineToRelative(-1.4f, -1.4f)
            quadToRelative(1.15f, -1.1f, 1.83f, -2.61f)
            quadTo(20f, 13.73f, 20f, 12f)
            quadTo(20f, 8.65f, 17.68f, 6.32f)
            reflectiveQuadTo(12f, 4f)
            reflectiveQuadTo(6.33f, 6.32f)
            reflectiveQuadTo(4f, 12f)
            quadToRelative(0f, 1.72f, 0.67f, 3.22f)
            reflectiveQuadToRelative(1.85f, 2.6f)
            lineTo(5.1f, 19.25f)
            close()
            moveTo(7.93f, 16.43f)
            quadTo(7.05f, 15.6f, 6.53f, 14.46f)
            reflectiveQuadTo(6f, 12f)
            quadTo(6f, 9.5f, 7.75f, 7.75f)
            reflectiveQuadTo(12f, 6f)
            reflectiveQuadToRelative(4.25f, 1.75f)
            reflectiveQuadTo(18f, 12f)
            quadToRelative(0f, 1.32f, -0.52f, 2.47f)
            reflectiveQuadToRelative(-1.4f, 1.95f)
            lineTo(14.65f, 15f)
            quadToRelative(0.63f, -0.58f, 0.99f, -1.35f)
            quadTo(16f, 12.88f, 16f, 12f)
            quadTo(16f, 10.35f, 14.83f, 9.17f)
            reflectiveQuadTo(12f, 8f)
            reflectiveQuadTo(9.18f, 9.17f)
            reflectiveQuadTo(8f, 12f)
            quadToRelative(0f, 0.9f, 0.36f, 1.66f)
            reflectiveQuadTo(9.35f, 15f)
            lineTo(7.93f, 16.43f)
            close()
          }
        }
        .build()
    return _podcasts!!
  }

private var _podcasts: ImageVector? = null
