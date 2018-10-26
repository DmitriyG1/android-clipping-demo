package ru.appkode.clippingdemo.ui

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout

class ClipPathStarDemo @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {

  private val mask = MaskPathStar(1.0f)

  private val rotateAnimator = ValueAnimator.ofFloat(
    0f,
    6.283f
  )

  private val radiusAnimator = ValueAnimator.ofFloat(
    0.45f,
    1f
  )

  init {
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
    canvas.clipPath(mask.getPath())
    super.dispatchDraw(canvas)
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
