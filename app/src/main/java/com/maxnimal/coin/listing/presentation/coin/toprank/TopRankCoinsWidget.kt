package com.maxnimal.coin.listing.presentation.coin.tobrank

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.lifecycle.LifecycleObserver
import org.koin.core.component.KoinComponent

class TopRankCoinsWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs), KoinComponent, LifecycleObserver {

}