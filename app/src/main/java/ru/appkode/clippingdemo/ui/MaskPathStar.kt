package ru.appkode.clippingdemo.ui

import android.graphics.Path


class MaskPathStar(
  factor: Float = 1f,
  private val isCutout: Boolean = false
) : MaskPath() {

  private val path = Path()
  var starSizeFactor = factor
  var innerRadiusFactor = 0.5f
  var rotation = 0f

  override fun rebuildPath(containerWidthPx: Int, containerHeightPx: Int): Path {
    path.reset()

    val padding = containerHeightPx * 0.1
    val starSize = containerWidthPx * starSizeFactor
    val starRadius = starSize / 2
    val starX = (containerWidthPx - starSize) / 2 + starRadius
    val starY = (padding + starRadius).toFloat()


    drawStar(path, starX, starY, starRadius, starRadius * innerRadiusFactor, 5, rotation)

    if (isCutout) {
      // Draw a rectangle counterclockwise around the star, so the figure inside was cut
      path.lineTo(containerWidthPx.toFloat(), containerHeightPx.toFloat())
      path.lineTo(containerWidthPx.toFloat(), 0f)
      path.lineTo(0f, 0f)
      path.lineTo(0f, containerHeightPx.toFloat())
      path.lineTo(containerWidthPx.toFloat(), containerHeightPx.toFloat())
      path.close()
    }

    return path
  }

  override fun getPath(): Path {
    return path
  }
}

private fun drawStar(path: Path, x: Float, y: Float, radius: Float, innerRadius: Float, numOfPt: Int, rotation: Float) {
  val  angleOffset = 0.942478 + rotation // 54 degrees

  val section = 2.0 * Math.PI / numOfPt

  path.moveTo(
    (x + radius * Math.cos(angleOffset)).toFloat(),
    (y + radius * Math.sin(angleOffset)).toFloat())
  path.lineTo(
    (x + innerRadius * Math.cos(angleOffset + section / 2.0)).toFloat(),
    (y + innerRadius * Math.sin(angleOffset + section / 2.0)).toFloat())

  for (i in 1 until numOfPt) {
    path.lineTo(
      (x + radius * Math.cos(angleOffset + section * i)).toFloat(),
      (y + radius * Math.sin(angleOffset + section * i)).toFloat())
    path.lineTo(
      (x + innerRadius * Math.cos(angleOffset + section * i + section / 2.0)).toFloat(),
      (y + innerRadius * Math.sin(angleOffset + section * i + section / 2.0)).toFloat())
  }

  path.close()
}
