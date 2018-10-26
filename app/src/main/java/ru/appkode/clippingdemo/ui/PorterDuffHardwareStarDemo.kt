package ru.appkode.clippingdemo.ui

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.os.Build
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.animation.*
import android.widget.FrameLayout

class PorterDuffHardwareStarDemo @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {

  private val mask: MaskPathStar
  private val paint: Paint

  private val rotateAnimator = ValueAnimator.ofFloat(
    0f,
    6.283f
  )

  private val radiusAnimator = ValueAnimator.ofFloat(
    0.45f,
    1f
  )

  init {
    setLayerType(View.LAYER_TYPE_HARDWARE, null)

    paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
      color = ContextCompat.getColor(context, android.R.color.white)
      xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    mask = MaskPathStar(1.0f, true)

    rotateAnimator.repeatCount = ValueAnimator.INFINITE
    rotateAnimator.repeatMode = ValueAnimator.RESTART
    rotateAnimator.duration = 4000 // one turn in 4 seconds
    rotateAnimator.interpolator = LinearInterpolator()
    rotateAnimator.addUpdateListener { valueAnimator ->
      mask.rotation = valueAnimator.animatedValue as Float
      mask.rebuildPath(measuredWidth, measuredHeight)

      invalidate()
    }

    radiusAnimator.repeatCount = ValueAnimator.INFINITE
    radiusAnimator.repeatMode = ValueAnimator.REVERSE
    radiusAnimator.duration = 800
    radiusAnimator.addUpdateListener { valueAnimator ->
      mask.innerRadiusFactor = valueAnimator.animatedValue as Float
      mask.rebuildPath(measuredWidth, measuredHeight)

      invalidate()
    }

    radiusAnimator.start()
    rotateAnimator.start()
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    mask.rebuildPath(measuredWidth, measuredHeight)
  }

  override fun dispatchDraw(canvas: Canvas) {
    val path = mask.getPath()

    val saveCount =
      if (Build.VERSION.SDK_INT >= 21)
        canvas.saveLayer(
          RectF(0f, 0f, width.toFloat(), height.toFloat()),
          null)
      else
        canvas.saveLayer(
          0f,
          0f,
          width.toFloat(),
          height.toFloat(),
          null,
          Canvas.ALL_SAVE_FLAG)

    super.dispatchDraw(canvas)


    canvas.drawPath(path, paint)

    canvas.restoreToCount(saveCount)
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    radiusAnimator.start()
    rotateAnimator.start()
  }

  override fun onDetachedFromWindow() {
    radiusAnimator.end()
    rotateAnimator.end()
    super.onDetachedFromWindow()
  }
}
