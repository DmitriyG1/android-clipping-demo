package ru.appkode.clippingdemo.ui

import android.graphics.Path
import android.graphics.RectF


class MaskPathRoundedCorners(topLefRadiusPx: Float = 0f,
                             topRightRadiusPx: Float = 0f,
                             bottomRightRadiusPx: Float = 0f,
                             bottomLefRadiusPx: Float = 0f) : MaskPath() {

  private val path = Path()
  private val radii = floatArrayOf(
    0f,
    0f,
    0f,
    0f,
    0f,
    0f,
    0f,
    0f
  )

  init {
    setRadius(topLefRadiusPx, topRightRadiusPx, bottomRightRadiusPx, bottomLefRadiusPx)
  }

  fun setRadius(topLefRadiusPx: Float,
                topRightRadiusPx: Float,
                bottomRightRadiusPx: Float,
                bottomLefRadiusPx: Float) {
    radii[0] = topLefRadiusPx
    radii[1] = topLefRadiusPx

    radii[2] = topRightRadiusPx
    radii[3] = topRightRadiusPx

    radii[4] = bottomRightRadiusPx
    radii[5] = bottomRightRadiusPx

    radii[6] = bottomLefRadiusPx
    radii[7] = bottomLefRadiusPx
  }

  override fun rebuildPath(containerWidthPx: Int, containerHeightPx: Int): Path {
    path.reset()
    path.addRoundRect(RectF(0f, 0f, containerWidthPx.toFloat(), containerHeightPx.toFloat()),
      radii,
      Path.Direction.CW)
    path.close()

    return path
  }

  override fun getPath(): Path {
    return path
  }
}