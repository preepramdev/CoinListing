package com.maxnimal.coin.listing.presentation.ui.coin.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maxnimal.coin.listing.R
import com.maxnimal.coin.listing.databinding.FragmentCoinListBinding
import com.maxnimal.coin.listing.domain.model.CoinModel
import com.maxnimal.coin.listing.presentation.ui.coin.detail.CoinDetailBottomSheetFragment
import com.maxnimal.coin.listing.presentation.ui.coin.list.adapter.CoinHorizontalItemAdapter
import com.maxnimal.coin.listing.presentation.ui.coin.list.adapter.HeaderStringResItemAdapter
import com.maxnimal.coin.listing.presentation.ui.coin.list.adapter.TopRankItemAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoinListFragment : Fragment() {

    companion object {
        private const val INTERVAL_REFRESH_TIME = 10000L
        private const val LIST_SPAN_SIZE_FULL = 3
        private const val LIST_SPAN_SIZE_HIDE = 0
        private const val SIZE_OF_TOP_RANK = 3
    }

    private val binding by lazy { FragmentCoinListBinding.inflate(layoutInflater) }
    private val viewModel: CoinListViewModel by viewModel()

    private val configConcatAdapter = ConcatAdapter.Config.Builder().apply {
        setIsolateViewTypes(false)
    }.build()

    private val headerTopRankAdapter = HeaderStringResItemAdapter(isVisible = false)
    private val topRankAdapter = TopRankItemAdapter()
    private val headerBuySellHoldItemAdapter = HeaderStringResItemAdapter(isVisible = false)
    private val coinHorizontalAdapter = CoinHorizontalItemAdapter()

    // todo note to prevent blink effect avoid using notify notifyDataSetChange
    private val coinListConcatAdapter = ConcatAdapter(
        configConcatAdapter,
        headerTopRankAdapter,
        topRankAdapter,
        headerBuySellHoldItemAdapter,
        coinHorizontalAdapter
    )

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
                delay(INTERVAL_REFRESH_TIME)
                if (binding.rvCoinList.scrollState != RecyclerView.SCROLL_STATE_DRAGGING) {
                    viewModel.updateCoins()
                }
            }
        }
    }

    private fun initView() = with(binding) {
        headerTopRankAdapter.updateTextHeader(R.string.text_top_rank_title)
        headerBuySellHoldItemAdapter.updateTextHeader(R.string.text_buy_sell_and_hold)
        topRankAdapter.onTopTierItemClick = { coinModel ->
            openCoinDetail(coinModel)
        }
        coinHorizontalAdapter.onCoinItemClick = { coinModel ->
            openCoinDetail(coinModel)
        }

        rvCoinList.visibility = View.GONE
        layoutError.visibility = View.GONE

        swReloadCoins.apply {
            setColorSchemeResources(R.color.loading)
            setOnRefreshListener {
                viewModel.reloadCoins()
            }
        }

        tvTryAgain.setOnClickListener {
            viewModel.reloadCoins()
        }

        rvCoinList.apply {
            adapter = coinListConcatAdapter
            layoutManager = GridLayoutManager(requireContext(), 3).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (coinListConcatAdapter.getItemViewType(position)) {
                            CoinListViewType.TOP_RANK.value -> LIST_SPAN_SIZE_FULL
                            CoinListViewType.OTHERS.value -> resources.getInteger(R.integer.coins_span_size)
                            CoinListViewType.TEXT_HEADER.value -> LIST_SPAN_SIZE_FULL
                            else -> LIST_SPAN_SIZE_HIDE
                        }
                    }
                }
            }
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0) {
                        val childCount = coinHorizontalAdapter.itemCount
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
            binding.rvCoinList.visibility = View.VISIBLE
            binding.layoutError.visibility = View.GONE
            val topThreeCoinList = coinModelList.take(SIZE_OF_TOP_RANK)
            val restOfCoinList = coinModelList.drop(SIZE_OF_TOP_RANK)
            topRankAdapter.submitList(topThreeCoinList)
            coinHorizontalAdapter.submitList(restOfCoinList)
        }

        showError.observe(viewLifecycleOwner) {
            binding.layoutError.visibility = View.VISIBLE
            binding.rvCoinList.visibility = View.GONE
            topRankAdapter.submitList(emptyList())
            coinHorizontalAdapter.submitList(emptyList())
            // todo using paging library
        }

        showErrorLoadMore.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Sorry, Something wrong", Toast.LENGTH_SHORT).show()
        }

        showLoading.observe(viewLifecycleOwner) {
            headerTopRankAdapter.setVisibility(isVisible = false)
            headerBuySellHoldItemAdapter.setVisibility(isVisible = false)
            binding.swReloadCoins.isRefreshing = true
        }

        hideLoading.observe(viewLifecycleOwner) {
            headerTopRankAdapter.setVisibility(isVisible = true)
            headerBuySellHoldItemAdapter.setVisibility(isVisible = true)
            binding.swReloadCoins.isRefreshing = false
        }
    }

    private fun openCoinDetail(coinModel: CoinModel) {
        findNavController().navigate(
            R.id.action_coinListFragment_to_coinDetailBottomSheetFragment,
            bundleOf(CoinDetailBottomSheetFragment.KEY_UUID to coinModel.uuid)
        )
    }
}