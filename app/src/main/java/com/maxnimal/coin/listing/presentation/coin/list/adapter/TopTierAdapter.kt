package com.maxnimal.coin.listing.presentation.coin.list.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maxnimal.coin.listing.databinding.ItemTopTierBinding
import com.maxnimal.coin.listing.domain.model.CoinModel

class TopTierAdapter : RecyclerView.Adapter<TopTierAdapter.TopTierViewHolder>() {

    private var coinModelList = mutableListOf<CoinModel>()
    private val topRankItemAdapter = TopRankItemAdapter()

    var onTopTierItemClick: ((CoinModel) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(coinModelList: List<CoinModel>) {
        this.coinModelList = coinModelList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopTierViewHolder {
        val binding = ItemTopTierBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TopTierViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopTierViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemViewType(position: Int) = CoinListViewType.TOP_TIER.value

    override fun getItemCount(): Int = 1

    inner class TopTierViewHolder(
        private val binding: ItemTopTierBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            topRankItemAdapter.apply {
                submitList(coinModelList)
                this.onTopRankItemClick = onTopTierItemClick
            }
            binding.rvTopTier.apply {
                adapter = topRankItemAdapter
                layoutManager = GridLayoutManager(binding.root.context, 3)
            }
        }
    }
}