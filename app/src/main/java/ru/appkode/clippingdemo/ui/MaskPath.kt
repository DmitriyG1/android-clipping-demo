package ru.appkode.clippingdemo.ui

import android.graphics.Path
import android.support.annotation.Px

abstract class MaskPath {
  abstract fun rebuildPath(@Px containerWidthPx: Int, @Px containerHeightPx: Int): Path
  abstract fun getPath(): Path
}