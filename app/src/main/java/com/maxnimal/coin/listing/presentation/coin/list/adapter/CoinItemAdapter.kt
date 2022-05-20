package com.maxnimal.coin.listing.presentation.coin.list.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.maxnimal.coin.listing.R
import com.maxnimal.coin.listing.databinding.ItemCoinBinding
import com.maxnimal.coin.listing.domain.model.CoinModel
import com.maxnimal.coin.listing.presentation.extension.loadImageFromUrl
import java.text.DecimalFormat

class CoinItemAdapter : RecyclerView.Adapter<CoinItemAdapter.CoinItemViewHolder>() {

    private var coinModelList = mutableListOf<CoinModel>()

    var onCoinItemClick: ((CoinModel) -> Unit)? = null

    fun submitList(coinModelList: List<CoinModel>) {
        this.coinModelList.addAll(coinModelList)
        notifyDataSetChanged()
    }

    fun updateList(coinModelList: List<CoinModel>) {
        this.coinModelList = coinModelList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinItemViewHolder {
        val binding = ItemCoinBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CoinItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinItemViewHolder, position: Int) {
        val coinModel = coinModelList[position]
        holder.bind(coinModel)
    }

    override fun getItemCount(): Int = coinModelList.size

    inner class CoinItemViewHolder(
        private val binding: ItemCoinBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(coin: CoinModel) = with(binding) {
            root.setOnClickListener {
                onCoinItemClick?.invoke(coin)
            }
            tvCoinName.text = coin.name
            tvCoinSymbol.text = coin.symbol
            ivCoinIcon.loadImageFromUrl(coin.iconUrl)
            tvCoinPrice.apply {
                val dec = DecimalFormat("#,##0.0000")
                val price = dec.format(coin.price)
                text = "$$price"
            }
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