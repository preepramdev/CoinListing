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
import com.maxnimal.coin.listing.presentation.extension.formatCurrency
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
            wgCoinChange.setChange(coin.change)
        }
    }
}