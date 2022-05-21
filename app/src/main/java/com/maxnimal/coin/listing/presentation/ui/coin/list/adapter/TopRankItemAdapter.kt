package com.maxnimal.coin.listing.presentation.ui.coin.list.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maxnimal.coin.listing.databinding.ItemTopRankBinding
import com.maxnimal.coin.listing.domain.model.CoinModel
import com.maxnimal.coin.listing.presentation.ui.coin.list.CoinListViewType

class TopRankItemAdapter : RecyclerView.Adapter<TopRankItemAdapter.TopRankItemViewHolder>() {

    private var coinModelList = mutableListOf<CoinModel>()
    private val topRankItemAdapter = CoinVerticalItemAdapter()

    var onTopTierItemClick: ((CoinModel) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(coinModelList: List<CoinModel>) {
        this.coinModelList = coinModelList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRankItemViewHolder {
        val binding = ItemTopRankBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TopRankItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopRankItemViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemViewType(position: Int) = CoinListViewType.TOP_TIER.value

    override fun getItemCount(): Int = 1

    inner class TopRankItemViewHolder(
        private val binding: ItemTopRankBinding
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