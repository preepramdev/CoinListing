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
import com.maxnimal.coin.listing.presentation.ui.coin.list.adapter.HeaderBuySellHoldItemAdapter
import com.maxnimal.coin.listing.presentation.ui.coin.list.adapter.HeaderTopRankItemAdapter
import com.maxnimal.coin.listing.presentation.ui.coin.list.adapter.TopRankItemAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoinListFragment : Fragment() {

    private val binding by lazy { FragmentCoinListBinding.inflate(layoutInflater) }
    private val viewModel: CoinListViewModel by viewModel()

    private val configConcatAdapter = ConcatAdapter.Config.Builder().apply {
        setIsolateViewTypes(false)
    }.build()
    private val headerTopRankAdapter = HeaderTopRankItemAdapter()
    private val topRankAdapter = TopRankItemAdapter()
    private val headerBuySellHoldItemAdapter = HeaderBuySellHoldItemAdapter()
    private val coinHorizontalAdapter = CoinHorizontalItemAdapter()
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
                delay(10000L)
                viewModel.updateCoins()
            }
        }
    }

    private fun initView() = with(binding) {
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
                            CoinListViewType.TOP_RANK.value -> 3
                            CoinListViewType.OTHERS.value -> resources.getInteger(R.integer.coins_span_size)
                            CoinListViewType.HEADER_TOP_RANK.value -> 3
                            CoinListViewType.HEADER_BUY_SELL_HOLD.value -> 3
                            else -> 0
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
            topRankAdapter.submitList(coinModelList.take(3))
            coinHorizontalAdapter.updateList(coinModelList.drop(3))
        }

        showError.observe(viewLifecycleOwner) {
            binding.layoutError.visibility = View.VISIBLE
            binding.rvCoinList.visibility = View.GONE
            topRankAdapter.submitList(emptyList())
            coinHorizontalAdapter.updateList(emptyList())
        }

        showErrorLoadMore.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Sorry, Something wrong", Toast.LENGTH_SHORT).show()
        }

        showLoading.observe(viewLifecycleOwner) {
            binding.swReloadCoins.isRefreshing = true
        }

        hideLoading.observe(viewLifecycleOwner) {
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