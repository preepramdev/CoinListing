package com.maxnimal.coin.listing.presentation.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.maxnimal.coin.listing.R
import com.maxnimal.coin.listing.databinding.WidgetCoinChangeBinding
import com.maxnimal.coin.listing.presentation.extension.formatCurrency

class CoinChangeWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val binding = WidgetCoinChangeBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    fun setChange(change: Double) = with(binding) {
        tvCoinChange.apply {
            val changeText = change.formatCurrency("#,##0.00")
            val textColor = when {
                change > 0.0 -> {
                    "#13BC24"
                }
                change < 0.0 -> {
                    "#F82D2D"
                }
                else -> null
            }
            textColor?.let { _textColor ->
                this.visibility = View.VISIBLE
                text = changeText.replace("-", "")
                setTextColor(Color.parseColor(_textColor))
            } ?: run {
                this.visibility = View.GONE
            }
        }
        ivChangeArrow.apply {
            val arrowIconResId = when {
                change > 0.0 -> {
                    R.drawable.ic_arrow_up_green
                }
                change < 0.0 -> {
                    R.drawable.ic_arrow_down_red
                }
                else -> null
            }
            arrowIconResId?.let { _arrowIconResId ->
                this.visibility = View.VISIBLE
                setImageDrawable(ContextCompat.getDrawable(this.context, _arrowIconResId))
            } ?: run {
                this.visibility = View.GONE
            }
        }
    }
}