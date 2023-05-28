package com.maxnimal.coin.listing.presentation.ui.coin.list.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.maxnimal.coin.listing.R
import com.maxnimal.coin.listing.databinding.ItemCoinHorizontalBinding
import com.maxnimal.coin.listing.domain.model.CoinModel
import com.maxnimal.coin.listing.presentation.extension.formatCurrency
import com.maxnimal.coin.listing.presentation.ui.coin.list.CoinListViewType
import com.maxnimal.coin.listing.presentation.extension.loadImageFromUrl
import java.text.DecimalFormat

class CoinHorizontalItemAdapter :
    ListAdapter<CoinModel, CoinHorizontalItemAdapter.CoinHorizontalItemViewHolder>(
        CoinHorizontalItemDiffCallBack()
    ) {

    companion object {
        private const val FORMAT_CURRENCY = "#,##0.0000"
    }

    var onCoinItemClick: ((CoinModel) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CoinHorizontalItemViewHolder {
        val binding = ItemCoinHorizontalBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CoinHorizontalItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinHorizontalItemViewHolder, position: Int) {
        val coinModel = getItem(position)
        holder.bind(coinModel)
    }

    override fun getItemViewType(position: Int) = CoinListViewType.OTHERS.value


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

    class CoinHorizontalItemDiffCallBack : DiffUtil.ItemCallback<CoinModel>() {

        override fun areItemsTheSame(oldItem: CoinModel, newItem: CoinModel): Boolean {
            return newItem.uuid == oldItem.uuid
        }

        override fun areContentsTheSame(oldItem: CoinModel, newItem: CoinModel): Boolean {
            return newItem == oldItem
        }

    }
}