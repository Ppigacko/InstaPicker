package com.ppigacko.instapostimagepicker.ext

import android.app.Activity
import android.content.Context
import android.graphics.Insets
import android.graphics.Rect
import android.os.Build
import android.util.DisplayMetrics
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import android.view.WindowMetrics
import androidx.annotation.LayoutRes


fun Context.getStatusBarHeight(): Int {
    var statusBar = 0
    val statusBarId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (statusBarId > 0) {
        statusBar = resources.getDimensionPixelSize(statusBarId)
    }
    return statusBar
}

fun Context.getNavigationBarHeight(): Int {
    var navigationBarHeight = 0
    val resourceIdBottom: Int =
        resources.getIdentifier("navigation_bar_height", "dimen", "android")
    if (resourceIdBottom > 0) {
        navigationBarHeight = resources.getDimensionPixelSize(resourceIdBottom)
    }

    return navigationBarHeight
}

fun Context.getRealDisplaySize(): Size {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

        val metrics: WindowMetrics =
            getSystemService(WindowManager::class.java).currentWindowMetrics

        val windowInsets: WindowInsets = metrics.windowInsets
        val insets: Insets = windowInsets.getInsetsIgnoringVisibility(
            WindowInsets.Type.navigationBars() or WindowInsets.Type.displayCutout()
        )

        val insetsWidth: Int = insets.right + insets.left
        val insetsHeight: Int = insets.top + insets.bottom

        val bounds: Rect = metrics.bounds

        return Size(
            bounds.width() - insetsWidth,
            bounds.height() - insetsHeight
        )
    } else {
        val displayMetrics = DisplayMetrics()
        (this as? Activity)?.windowManager?.defaultDisplay?.getRealMetrics(displayMetrics)

        return Size(
            displayMetrics.widthPixels,
            displayMetrics.heightPixels - getStatusBarHeight() - getNavigationBarHeight()
        )
    }
}

fun ViewGroup.createView(@LayoutRes layoutId: Int): View =
    LayoutInflater.from(context).inflate(layoutId, this, false)