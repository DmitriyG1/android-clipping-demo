package ru.appkode.clippingdemo.ui

import android.graphics.Path
import android.graphics.RectF

class MaskPathRoundedCutout(topLefRadiusPx: Float = 0f,
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
    // TODO bottom corners
    addTopLeftCorner(path, 0f, 0f, radii[0], radii[1])
    addTopRightCorner(path, containerWidthPx.toFloat(), 0f, radii[2], radii[3])
    path.close()
    return path
  }

  override fun getPath(): Path {
    return path
  }

  private fun addTopLeftCorner(path: Path, x: Float, y: Float, radiusX: Float, radiusY: Float) {
    path.moveTo(x, y)
    path.lineTo(x, y + radiusY)
    path.arcTo(RectF(x, y, x + radiusX * 2, y + radiusY * 2), 180f, 90f)
    path.lineTo(x, y)
  }

  private fun addTopRightCorner(path: Path, x: Float, y: Float, radiusX: Float, radiusY: Float) {
    path.moveTo(x - radiusX, y)
    path.arcTo(RectF(x - radiusX * 2, y, x, y + radiusY * 2), 270f, 90f)
    path.lineTo(x, y)
    path.lineTo(x - radiusX, y)
  }
}