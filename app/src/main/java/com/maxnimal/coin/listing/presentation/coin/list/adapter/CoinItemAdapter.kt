package com.maxnimal.coin.listing.presentation.coin.list.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import coil.request.ImageRequest
import com.maxnimal.coin.listing.databinding.ItemCoinBinding
import com.maxnimal.coin.listing.domain.model.CoinModel
import com.maxnimal.coin.listing.presentation.extension.loadImageFromUrl

class CoinItemAdapter : RecyclerView.Adapter<CoinItemAdapter.CoinItemViewHolder>() {

    private val coinModelList = mutableListOf<CoinModel>()

    var onCoinItemClick: ((CoinModel) -> Unit)? = null

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
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(coin: CoinModel) = with(binding) {
            root.setOnClickListener {
                onCoinItemClick?.invoke(coin)
            }
            tvCoinName.text = coin.name
            tvCoinSymbol.text = coin.symbol
            ivCoinIcon.loadImageFromUrl(coin.iconUrl)
        }
    }
}