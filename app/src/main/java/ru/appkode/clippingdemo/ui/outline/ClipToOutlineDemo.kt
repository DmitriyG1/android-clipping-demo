package ru.appkode.clippingdemo.ui.outline

import android.annotation.TargetApi
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import ru.appkode.clippingdemo.R
import ru.appkode.clippingdemo.ui.AppBarDelegate
import ru.appkode.clippingdemo.ui.AppBarState


@TargetApi(21)
class ClipToOutlineDemo @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {

  private val appBarDelegate: AppBarDelegate

  init {
    val cornerRadius = context.resources.getDimension(R.dimen.corner_radius)
    val backgroundDrawable = RoundRectDrawable(android.R.color.white, cornerRadius)
    background = backgroundDrawable
    clipToOutline = true

    val collapsedRadius = 0f
    appBarDelegate = AppBarDelegate(
      this,
      { state ->
        val radius =
          when (state) {
            AppBarState.EXPANDED -> cornerRadius
            AppBarState.COLLAPSED -> collapsedRadius
            AppBarState.IDLE -> cornerRadius
          }
        backgroundDrawable.setRadius(radius)
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
}
