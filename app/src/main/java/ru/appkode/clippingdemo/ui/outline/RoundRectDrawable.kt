package ru.appkode.clippingdemo.ui.outline

import android.annotation.TargetApi
import android.graphics.*
import android.graphics.drawable.Drawable

@TargetApi(21)
class RoundRectDrawable(backgroundColor: Int, initRadius: Float) : Drawable() {
  private var radius: Float
  private var paint: Paint
  private var boundsF: RectF
  private var boundsI: Rect

  init{
    radius = initRadius
    paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
    paint.color = backgroundColor
    boundsF = RectF()
    boundsI = Rect()
  }

  override fun draw(canvas: Canvas) {
    canvas.drawRoundRect(boundsF, radius, radius, paint)
  }

  private fun updateBounds(boundsToUpdate: Rect?) {
    val boundsResult = boundsToUpdate ?: getBounds()
    boundsF.set(boundsResult!!.left.toFloat(),
      boundsResult.top.toFloat(),
      boundsResult.right.toFloat(),
      boundsResult.bottom.toFloat())
    boundsI.set(boundsResult)
  }

  override fun onBoundsChange(bounds: Rect) {
    super.onBoundsChange(bounds)
    updateBounds(bounds)
  }

  override fun getOutline(outline: Outline) {
    outline.setRoundRect(boundsI, radius)
  }

  fun setRadius(radius: Float) {
    if (radius == this.radius) {
      return
    }
    this.radius = radius
    updateBounds(null)
    invalidateSelf()
  }

  override fun getOpacity(): Int {
    return PixelFormat.OPAQUE
  }

  override fun setAlpha(alpha: Int) {}
  override fun setColorFilter(cf: ColorFilter?) {}
}