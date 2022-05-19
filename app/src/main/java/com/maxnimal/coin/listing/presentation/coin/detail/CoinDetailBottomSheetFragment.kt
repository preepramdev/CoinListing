package com.maxnimal.coin.listing.presentation.coin.detail

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.maxnimal.coin.listing.R
import com.maxnimal.coin.listing.databinding.BottomSheetCoinDetailBinding

class CoinDetailBottomSheetFragment : BottomSheetDialogFragment() {

    private val binding by lazy { BottomSheetCoinDetailBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(STYLE_NO_FRAME, R.style.BottomSheetDialog)
        return super.onCreateDialog(savedInstanceState)
    }
}