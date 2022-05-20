package com.maxnimal.coin.listing.presentation.coin.list.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maxnimal.coin.listing.databinding.ItemTopRankBinding
import com.maxnimal.coin.listing.domain.model.CoinModel
import com.maxnimal.coin.listing.presentation.extension.loadImageFromUrl
import java.text.DecimalFormat

class TopRankItemAdapter : RecyclerView.Adapter<TopRankItemAdapter.TopRankItemVieHolder>() {

    private var coinModelList = mutableListOf<CoinModel>()

    var onTopRankItemClick: ((CoinModel) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(coinModelList: List<CoinModel>) {
        this.coinModelList = coinModelList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRankItemVieHolder {
        val binding = ItemTopRankBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TopRankItemVieHolder(binding)
    }

    override fun onBindViewHolder(holder: TopRankItemVieHolder, position: Int) {
        val coinModel = coinModelList[position]
        holder.bind(coinModel)
    }

    override fun getItemCount(): Int = coinModelList.size

    inner class TopRankItemVieHolder(
        private val binding: ItemTopRankBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(coin: CoinModel) = with(binding) {
            root.setOnClickListener {
                onTopRankItemClick?.invoke(coin)
            }
            ivCoinIcon.loadImageFromUrl(coin.iconUrl)
            tvCoinName.text = coin.name
            tvCoinSymbol.text = coin.symbol
        }
    }
}