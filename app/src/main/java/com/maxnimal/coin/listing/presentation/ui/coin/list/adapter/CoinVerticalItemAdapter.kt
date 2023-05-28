package com.maxnimal.coin.listing.presentation.ui.coin.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.maxnimal.coin.listing.databinding.ItemCoinVerticalBinding
import com.maxnimal.coin.listing.domain.model.CoinModel
import com.maxnimal.coin.listing.presentation.extension.loadImageFromUrl

class CoinVerticalItemAdapter : ListAdapter<CoinModel, CoinVerticalItemAdapter.CoinVerticalItemVieHolder>(CoinVerticalItemDiffCallBack()) {

    var onTopRankItemClick: ((CoinModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinVerticalItemVieHolder {
        val binding = ItemCoinVerticalBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CoinVerticalItemVieHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinVerticalItemVieHolder, position: Int) {
        val coinModel = getItem(position)
        holder.bind(coinModel)
    }

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

    class CoinVerticalItemDiffCallBack : DiffUtil.ItemCallback<CoinModel>() {

        override fun areItemsTheSame(oldItem: CoinModel, newItem: CoinModel): Boolean {
            return newItem.uuid == oldItem.uuid
        }

        override fun areContentsTheSame(oldItem: CoinModel, newItem: CoinModel): Boolean {
            return newItem == oldItem
        }

    }
}