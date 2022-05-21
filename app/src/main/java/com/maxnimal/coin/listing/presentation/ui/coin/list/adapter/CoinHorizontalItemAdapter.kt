package com.maxnimal.coin.listing.presentation.ui.coin.list.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.maxnimal.coin.listing.R
import com.maxnimal.coin.listing.databinding.ItemCoinHorizontalBinding
import com.maxnimal.coin.listing.domain.model.CoinModel
import com.maxnimal.coin.listing.presentation.extension.formatCurrency
import com.maxnimal.coin.listing.presentation.ui.coin.list.CoinListViewType
import com.maxnimal.coin.listing.presentation.extension.loadImageFromUrl
import java.text.DecimalFormat

class CoinHorizontalItemAdapter : RecyclerView.Adapter<CoinHorizontalItemAdapter.CoinHorizontalItemViewHolder>() {

    companion object {
        private const val FORMAT_CURRENCY = "#,##0.0000"
    }

    private var coinModelList = mutableListOf<CoinModel>()

    var onCoinItemClick: ((CoinModel) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(coinModelList: List<CoinModel>) {
        this.coinModelList = coinModelList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinHorizontalItemViewHolder {
        val binding = ItemCoinHorizontalBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CoinHorizontalItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinHorizontalItemViewHolder, position: Int) {
        val coinModel = coinModelList[position]
        holder.bind(coinModel)
    }

    override fun getItemViewType(position: Int) = CoinListViewType.OTHERS.value

    override fun getItemCount(): Int = coinModelList.size

    inner class CoinHorizontalItemViewHolder(
        private val binding: ItemCoinHorizontalBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(coin: CoinModel) = with(binding) {
            root.setOnClickListener {
                onCoinItemClick?.invoke(coin)
            }
            ivCoinIcon.loadImageFromUrl(coin.iconUrl)
            tvCoinName.text = coin.name
            tvCoinSymbol.text = coin.symbol
            tvCoinPrice.text = "$${coin.price.formatCurrency(FORMAT_CURRENCY)}"
            wgCoinChange.setChange(coin.change)
        }
    }
}