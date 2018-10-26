package ru.appkode.clippingdemo.ui

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.FrameLayout
import ru.appkode.clippingdemo.R

class ClipPathDemo @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {

  private val appBarDelegate: AppBarDelegate
  private val maskPath: MaskPath

  init {
    val cornerRadius = context.resources.getDimension(R.dimen.corner_radius)
    val collapsedRadius = 0f
    maskPath = MaskPathRoundedCorners(cornerRadius, cornerRadius)


    appBarDelegate = AppBarDelegate(
      this,
      { state ->
        val radius =
          when (state) {
            AppBarState.EXPANDED -> cornerRadius
            AppBarState.COLLAPSED -> collapsedRadius
            AppBarState.IDLE -> cornerRadius
          }
        maskPath.setRadius(radius, radius, 0f, 0f)
        maskPath.rebuildPath(measuredWidth, measuredHeight)
        invalidate()
      }
    )
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    appBarDelegate.onAttachedToWindow()
  }

  override fun onDetachedFromWindow() {
    appBarDelegate.onDetachedFromWindow()
    super.onDetachedFromWindow()
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    maskPath.rebuildPath(measuredWidth, measuredHeight)
  }

  override fun dispatchDraw(canvas: Canvas) {
    canvas.clipPath(maskPath.getPath())
    super.dispatchDraw(canvas)
  }
}
