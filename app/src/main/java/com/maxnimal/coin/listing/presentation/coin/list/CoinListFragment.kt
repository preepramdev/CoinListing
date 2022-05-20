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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maxnimal.coin.listing.R
import com.maxnimal.coin.listing.databinding.FragmentCoinListBinding
import com.maxnimal.coin.listing.presentation.coin.detail.CoinDetailBottomSheetFragment
import com.maxnimal.coin.listing.presentation.coin.list.adapter.CoinItemAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoinListFragment : Fragment() {

    private val binding by lazy { FragmentCoinListBinding.inflate(layoutInflater) }
    private val viewModel: CoinListViewModel by viewModel()

    private val coinItemAdapter = CoinItemAdapter()

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
        coinItemAdapter.onCoinItemClick = { coinModel ->
            findNavController().navigate(
                R.id.action_coinListFragment_to_coinDetailBottomSheetFragment,
                bundleOf(CoinDetailBottomSheetFragment.KEY_UUID to coinModel.uuid)
            )
        }

        rvCoinList.apply {
            adapter = coinItemAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0) {
                        val childCount = coinItemAdapter.itemCount
                        val lastPosition =
                            (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
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
            coinItemAdapter.updateList(coinModelList)
        }
    }
}