package ru.appkode.clippingdemo.ui

import android.support.design.widget.AppBarLayout

enum class AppBarState {
  EXPANDED,
  COLLAPSED,
  IDLE
}

class AppBarStateChangeListener(private val onStateChanged: ((AppBarLayout, AppBarState) -> Unit)? = null)
  : AppBarLayout.OnOffsetChangedListener {
  private var currentState = AppBarState.IDLE

  override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
    val prevState = currentState
    when {
      verticalOffset == 0 -> {
        currentState = AppBarState.EXPANDED
      }
      Math.abs(verticalOffset) >= appBarLayout.totalScrollRange -> {
        currentState = AppBarState.COLLAPSED
      }
      else -> {
        currentState = AppBarState.IDLE
      }
    }

    if (currentState !== prevState) {
      onStateChanged(appBarLayout, currentState)
    }
  }

  private fun onStateChanged(appBarLayout: AppBarLayout, state: AppBarState) {
    onStateChanged?.invoke(appBarLayout, state)
  }
}