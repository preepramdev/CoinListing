package com.maxnimal.coin.listing.presentation.ui.coin.list.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.maxnimal.coin.listing.R
import com.maxnimal.coin.listing.databinding.ItemCoinVerticalBinding
import com.maxnimal.coin.listing.domain.model.CoinModel
import com.maxnimal.coin.listing.presentation.extension.loadImageFromUrl
import java.text.DecimalFormat

class CoinVerticalItemAdapter : RecyclerView.Adapter<CoinVerticalItemAdapter.CoinVerticalItemVieHolder>() {

    private var coinModelList = mutableListOf<CoinModel>()

    var onTopRankItemClick: ((CoinModel) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(coinModelList: List<CoinModel>) {
        this.coinModelList = coinModelList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinVerticalItemVieHolder {
        val binding = ItemCoinVerticalBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CoinVerticalItemVieHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinVerticalItemVieHolder, position: Int) {
        val coinModel = coinModelList[position]
        holder.bind(coinModel)
    }

    override fun getItemCount(): Int = coinModelList.size

    inner class CoinVerticalItemVieHolder(
        private val binding: ItemCoinVerticalBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(coin: CoinModel) = with(binding) {
            root.setOnClickListener {
                onTopRankItemClick?.invoke(coin)
            }
            ivCoinIcon.loadImageFromUrl(coin.iconUrl)
            tvCoinName.text = coin.name
            tvCoinSymbol.text = coin.symbol
            tvCoinChange.apply {
                val dec = DecimalFormat("#,##0.00")
                val change = dec.format(coin.change)
                val textColor = when {
                    coin.change > 0.0 -> {
                        "#13BC24"
                    }
                    coin.change < 0.0 -> {
                        "#F82D2D"
                    }
                    else -> null
                }
                textColor?.let { _textColor ->
                    this.visibility = View.VISIBLE
                    text = change.replace("-", "")
                    setTextColor(Color.parseColor(_textColor))
                } ?: run {
                    this.visibility = View.GONE
                }
            }
            ivChangeArrow.apply {
                val arrowIconResId = when {
                    coin.change > 0.0 -> {
                        R.drawable.ic_arrow_up_green
                    }
                    coin.change < 0.0 -> {
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
}