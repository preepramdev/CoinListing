package com.maxnimal.coin.listing.presentation.ui.coin.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maxnimal.coin.listing.databinding.ItemHeaderBuySellHoldBinding
import com.maxnimal.coin.listing.presentation.ui.coin.list.CoinListViewType

class HeaderBuySellHoldItemAdapter :
    RecyclerView.Adapter<HeaderBuySellHoldItemAdapter.HeaderBuySellHoldItemViewHolder>() {

    companion object {
        private const val ITEM_COUNT = 1
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HeaderBuySellHoldItemViewHolder {
        val binding = ItemHeaderBuySellHoldBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HeaderBuySellHoldItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeaderBuySellHoldItemViewHolder, position: Int) {}

    override fun getItemViewType(position: Int): Int = CoinListViewType.HEADER_BUY_SELL_HOLD.value

    override fun getItemCount(): Int = ITEM_COUNT

    inner class HeaderBuySellHoldItemViewHolder(
        private val binding: ItemHeaderBuySellHoldBinding
    ) : RecyclerView.ViewHolder(binding.root)
}