package ru.appkode.clippingdemo.ui

import android.content.Context
import android.graphics.*
import android.os.Build
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import ru.appkode.clippingdemo.R

class PorterDuffHardwareDemo @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {

  private val appBarDelegate: AppBarDelegate
  private val mask: MaskPath
  private val paint: Paint

  init {
    setLayerType(View.LAYER_TYPE_HARDWARE, null)

    paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
      color = ContextCompat.getColor(context, android.R.color.white)
      xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
    }

    val cornerRadius = context.resources.getDimension(R.dimen.corner_radius)
    val collapsedRadius = 0f
    mask = MaskPathRoundedCorners(cornerRadius, cornerRadius)
    appBarDelegate = AppBarDelegate(
      this,
      { state ->
        val radius =
          when (state) {
            AppBarState.EXPANDED -> cornerRadius
            AppBarState.COLLAPSED -> collapsedRadius
            AppBarState.IDLE -> cornerRadius
          }
        mask.setRadius(radius, radius, 0f, 0f)
        mask.rebuildPath(measuredWidth, measuredHeight)
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
}
