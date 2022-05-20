package com.maxnimal.coin.listing.presentation.coin.list.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.maxnimal.coin.listing.databinding.ItemCoinBinding
import com.maxnimal.coin.listing.domain.model.CoinModel

class CoinItemAdapter: RecyclerView.Adapter<CoinItemAdapter.CoinItemViewHolder>() {

    private val coinModelList = mutableListOf<CoinModel>()

    fun submitList(coinModelList: List<CoinModel>) {
        this.coinModelList.addAll(coinModelList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinItemViewHolder {
        val binding = ItemCoinBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CoinItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinItemViewHolder, position: Int) {
        val coinModel = coinModelList[position]
        holder.bind(coinModel)
    }

    override fun getItemCount(): Int = coinModelList.size

    inner class CoinItemViewHolder(
        private val binding: ItemCoinBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(coin: CoinModel) = with(binding) {
            tvCoinName.text = coin.name.orEmpty()
            tvCoinSymbol.text = coin.symbol.orEmpty()
            GlideToVectorYou
                .init()
                .with(binding.root.context)
                .load(Uri.parse(coin.iconUrl), ivCoinIcon)
        }
    }
}