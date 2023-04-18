package com.maxnimal.coin.listing.presentation.ui.coin.list.adapter.header

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maxnimal.coin.listing.databinding.ItemHeaderTopRankBinding
import com.maxnimal.coin.listing.presentation.ui.coin.list.CoinListViewType

class HeaderTopRankItemAdapter: RecyclerView.Adapter<HeaderTopRankItemAdapter.HeaderItemTopRankViewHolder>() {

    companion object {
        private const val ITEM_COUNT = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderItemTopRankViewHolder {
        val binding = ItemHeaderTopRankBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HeaderItemTopRankViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeaderItemTopRankViewHolder, position: Int) {}

    override fun getItemViewType(position: Int): Int = CoinListViewType.HEADER_TOP_RANK.value

    override fun getItemCount(): Int = ITEM_COUNT

    inner class HeaderItemTopRankViewHolder(
        binding: ItemHeaderTopRankBinding
    ) : RecyclerView.ViewHolder(binding.root)
}