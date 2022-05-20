package com.maxnimal.coin.listing.presentation.coin.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maxnimal.coin.listing.R
import com.maxnimal.coin.listing.databinding.FragmentCoinListBinding
import com.maxnimal.coin.listing.presentation.coin.detail.CoinDetailBottomSheetFragment
import com.maxnimal.coin.listing.presentation.coin.list.adapter.CoinItemAdapter
import com.maxnimal.coin.listing.presentation.coin.list.adapter.CoinListViewType
import com.maxnimal.coin.listing.presentation.coin.list.adapter.TopTierAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoinListFragment : Fragment() {

    private val binding by lazy { FragmentCoinListBinding.inflate(layoutInflater) }
    private val viewModel: CoinListViewModel by viewModel()

    private val config = ConcatAdapter.Config.Builder().apply {
        setIsolateViewTypes(false)
    }.build()
    private val topTierAdapter = TopTierAdapter()
    private val coinItemAdapter = CoinItemAdapter()
    private val concatAdapter = ConcatAdapter(config, topTierAdapter, coinItemAdapter)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observeViewModel()
        viewModel.getCoins()
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launchWhenResumed {
            while (isActive) {
                delay(10000L)
                viewModel.refreshCoins()
            }
        }
    }

    private fun initView() = with(binding) {
        topTierAdapter.onTopTierItemClick = { coinModel ->
            findNavController().navigate(
                R.id.action_coinListFragment_to_coinDetailBottomSheetFragment,
                bundleOf(CoinDetailBottomSheetFragment.KEY_UUID to coinModel.uuid)
            )

        }
        coinItemAdapter.onCoinItemClick = { coinModel ->
            findNavController().navigate(
                R.id.action_coinListFragment_to_coinDetailBottomSheetFragment,
                bundleOf(CoinDetailBottomSheetFragment.KEY_UUID to coinModel.uuid)
            )
        }

        rvCoinList.apply {
            adapter = concatAdapter
            layoutManager = GridLayoutManager(requireContext(), 3).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (concatAdapter.getItemViewType(position)) {
                            CoinListViewType.TOP_TIER.value -> 3
                            CoinListViewType.OTHERS.value -> 3
                            else -> 0
                        }
                    }
                }
            }
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0) {
                        val childCount = coinItemAdapter.itemCount
                        val lastPosition =
                            (layoutManager as GridLayoutManager).findLastCompletelyVisibleItemPosition()
                        if (childCount - 1 == lastPosition) {
                            viewModel.getCoins()
                        }
                    }
                }
            })
        }
    }

    private fun observeViewModel() = with(viewModel) {
        showCoinList.observe(viewLifecycleOwner) { coinModelList ->
            topTierAdapter.submitList(coinModelList.take(3))
            coinItemAdapter.updateList(coinModelList.drop(3))
        }
    }
}