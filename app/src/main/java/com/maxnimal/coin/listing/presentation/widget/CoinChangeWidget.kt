package com.maxnimal.coin.listing.presentation.widget

import android.content.Context
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

    companion object {
        private const val FORMAT_CURRENCY = "#,##0.00"
        private const val ZERO_CHANGE = 0.0
    }

    private val binding = WidgetCoinChangeBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    fun setChange(change: Double) = with(binding) {
        tvCoinChange.apply {
            val changeText = change.formatCurrency(FORMAT_CURRENCY)
            val colorResId = when {
                (change > ZERO_CHANGE) -> R.color.change_up
                (change < ZERO_CHANGE) -> R.color.change_down
                else -> null
            }
            colorResId?.let { _colorResId ->
                this.visibility = View.VISIBLE
                text = changeText.replace("-", "")
                setTextColor(ContextCompat.getColor(context, _colorResId))
            } ?: run {
                this.visibility = View.GONE
            }
        }
        ivChangeArrow.apply {
            val arrowIconResId = when {
                (change > ZERO_CHANGE) -> R.drawable.ic_arrow_up_green
                (change < ZERO_CHANGE) -> R.drawable.ic_arrow_down_red
                else -> null
            }
            arrowIconResId?.let { _arrowIconResId ->
                this.visibility = View.VISIBLE
                setImageDrawable(ContextCompat.getDrawable(context, _arrowIconResId))
            } ?: run {
                this.visibility = View.GONE
            }
        }
    }
}