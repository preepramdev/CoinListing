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

    companion object {
        private const val ITEM_COUNT = 1
        private const val SPAN_SIZE = 3
    }

    private var coinModelList = mutableListOf<CoinModel>()
    private val coinVerticalItemAdapter = CoinVerticalItemAdapter()

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

    override fun getItemViewType(position: Int) = CoinListViewType.TOP_RANK.value

    override fun getItemCount(): Int = ITEM_COUNT

    inner class TopRankItemViewHolder(
        private val binding: ItemTopRankBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            coinVerticalItemAdapter.apply {
                submitList(coinModelList)
                this.onTopRankItemClick = onTopTierItemClick
            }
            binding.rvTopTier.apply {
                adapter = coinVerticalItemAdapter
                layoutManager = GridLayoutManager(binding.root.context, SPAN_SIZE)
            }
        }
    }
}