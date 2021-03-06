package com.maxnimal.coin.listing.presentation.ui.coin.detail

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.maxnimal.coin.listing.R
import com.maxnimal.coin.listing.databinding.BottomSheetCoinDetailBinding
import com.maxnimal.coin.listing.domain.model.CoinModel
import com.maxnimal.coin.listing.presentation.extension.formatCurrency
import com.maxnimal.coin.listing.presentation.extension.loadImageFromUrl
import com.maxnimal.coin.listing.presentation.extension.setTextHtml
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat


class CoinDetailBottomSheetFragment : BottomSheetDialogFragment() {

    companion object {
        const val KEY_UUID = "uuid"
    }

    private val binding by lazy { BottomSheetCoinDetailBinding.inflate(layoutInflater) }
    private val viewModel: CoinDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            viewModel.setUuid(bundle.getString(KEY_UUID))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(STYLE_NO_FRAME, R.style.BottomSheetDialog)
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeViewModel()
        viewModel.getCoin()
    }

    private fun initView() = with(binding) {
        pgbLoading.visibility = View.GONE
        layoutCoinDetail.visibility = View.GONE
        layoutError.visibility = View.GONE
        layoutErrorUuid.visibility = View.GONE

        tvTryAgain.setOnClickListener {
            viewModel.getCoin()
        }

        tvClose.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeViewModel() = with(viewModel) {
        showCoin.observe(viewLifecycleOwner) { coin ->
            showCoinDetail(coin)
        }

        showError.observe(viewLifecycleOwner) {
            binding.apply {
                layoutError.visibility = View.VISIBLE
                layoutErrorUuid.visibility = View.GONE
                layoutCoinDetail.visibility = View.GONE
            }
        }

        showErrorUuid.observe(viewLifecycleOwner) {
            binding.apply {
                layoutErrorUuid.visibility = View.VISIBLE
                layoutError.visibility = View.GONE
                layoutCoinDetail.visibility = View.GONE
            }
        }

        showLoading.observe(viewLifecycleOwner) {
            binding.pgbLoading.visibility = View.VISIBLE
        }

        hideLoading.observe(viewLifecycleOwner) {
            binding.pgbLoading.visibility = View.GONE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showCoinDetail(coin: CoinModel) = with(binding) {
        layoutCoinDetail.visibility = View.VISIBLE
        layoutError.visibility = View.GONE
        layoutErrorUuid.visibility = View.GONE
        ivCoinIcon.loadImageFromUrl(coin.iconUrl)
        tvCoinName.apply {
            runCatching {
                Color.parseColor(coin.color)
            }.onSuccess { colorInt ->
                setTextColor(colorInt)
            }
            text = coin.name
        }
        tvCoinSymbol.text = coin.symbol
        tvCoinDetail.setTextHtml(coin.description)
        tvCoinPrice.text = "$${coin.price.formatCurrency("#,##0.0000")}"
        tvCoinMarketCap.text = "$ ${coin.marketCap}"
        if (coin.websiteUrl.isNotBlank()) {
            layoutGoToWebsite.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(coin.websiteUrl)
                    startActivity(intent)
                }
            }
        } else {
            layoutGoToWebsite.visibility = View.GONE
        }
    }
}