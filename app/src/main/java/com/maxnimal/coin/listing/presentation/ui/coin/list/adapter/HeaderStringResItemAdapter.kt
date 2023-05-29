package com.maxnimal.coin.listing.presentation.ui.coin.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maxnimal.coin.listing.databinding.ItemHeaderStringResBinding
import com.maxnimal.coin.listing.presentation.ui.coin.list.CoinListViewType

class HeaderStringResItemAdapter(
    private var stringResId: Int? = null,
    private var isVisible: Boolean = false
) : RecyclerView.Adapter<HeaderStringResItemAdapter.TextHeaderItemViewHolder>() {

    companion object {
        private const val ITEM_COUNT = 1
        private const val ITEM_INDEX = 0
    }

    fun updateTextHeader(stringResId: Int) {
        this.stringResId = stringResId
        notifyItemChanged(ITEM_INDEX)
    }

    fun setVisibility(isVisible: Boolean) {
        this.isVisible = isVisible
        notifyItemChanged(ITEM_INDEX)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextHeaderItemViewHolder {
        val binding = ItemHeaderStringResBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TextHeaderItemViewHolder(binding)
    }

    override fun getItemCount(): Int = ITEM_COUNT

    override fun getItemViewType(position: Int): Int = CoinListViewType.TEXT_HEADER.value

    override fun onBindViewHolder(holder: TextHeaderItemViewHolder, position: Int) {
        stringResId?.let { holder.bind(it, isVisible) }
    }

    inner class TextHeaderItemViewHolder(
        private val binding: ItemHeaderStringResBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(stringResId: Int, isVisible: Boolean) {
            binding.tvTextHeader.apply {
                setText(stringResId)
                val visibility = if (isVisible) View.VISIBLE else View.GONE
                setVisibility(visibility)
            }
        }
    }
}