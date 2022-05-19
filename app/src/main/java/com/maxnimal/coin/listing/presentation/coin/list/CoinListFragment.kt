package com.maxnimal.coin.listing.presentation.coin.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.maxnimal.coin.listing.R
import com.maxnimal.coin.listing.databinding.FragmentCoinListBinding

class CoinListFragment : Fragment() {

    private val binding by lazy { FragmentCoinListBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() = with(binding){
        btnOpen.setOnClickListener {
            findNavController().navigate(R.id.action_coinListFragment_to_coinDetailBottomSheetFragment)
        }
    }
}