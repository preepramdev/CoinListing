package com.maxnimal.coin.listing.presentation.extension

import android.app.Activity
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat

fun Activity.setStatusBar(colorResId: Int, isNeedDark: Boolean) {
    val window: Window = this.window
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = ContextCompat.getColor(this, colorResId)
    if (isNeedDark) {
        window.decorView.systemUiVisibility = 0
    } else {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}