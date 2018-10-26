package ru.appkode.clippingdemo.ui

import android.support.design.widget.AppBarLayout
import android.util.Log
import android.view.View
import android.view.ViewGroup

class AppBarDelegate(
  private val view: View,
  private val callback: (AppBarState) -> Unit) {
  private var appBarLayout: AppBarLayout? = null
  private val appBarStateChangeListener = AppBarStateChangeListener { _, appBarState -> callback.invoke(appBarState) }

  fun onAttachedToWindow() {
    init()
  }

  fun onDetachedFromWindow() {
    unRegisterAppBarLayoutObserver()
  }

  private fun unRegisterAppBarLayoutObserver() {
    appBarLayout?.removeOnOffsetChangedListener(appBarStateChangeListener)
  }

  private fun registerAppBarLayoutObserver(appBarLayout: AppBarLayout) {
    appBarLayout.addOnOffsetChangedListener(appBarStateChangeListener)
  }

  private fun init() {
    unRegisterAppBarLayoutObserver()

    appBarLayout = findAppBarLayout()
    val appBar = appBarLayout
    if (appBar != null) {
      registerAppBarLayoutObserver(appBar)
    } else {
      Log.e("AppBarDelegate","Warning! AppBarLayout not found")    }
  }

  private fun findAppBarLayout(): AppBarLayout? {
    var parent = view.parent
    while (parent != null) {
      val viewGroup = parent as ViewGroup
      (0 until viewGroup.childCount).forEach { index ->
        val view = viewGroup.getChildAt(index)
        if (view is AppBarLayout) return view
      }
      parent = parent.parent
    }

    return null
  }
}
