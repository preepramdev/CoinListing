package com.maxnimal.coin.listing.presentation.coin.detail

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.maxnimal.coin.listing.R
import com.maxnimal.coin.listing.databinding.BottomSheetCoinDetailBinding
import com.maxnimal.coin.listing.domain.model.CoinModel
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

    }

    private fun observeViewModel() = with(viewModel) {
        showCoin.observe(viewLifecycleOwner) { coin ->
            showCoinDetail(coin)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showCoinDetail(coin: CoinModel) = with(binding) {
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
        tvCoinPrice.apply {
            val dec = DecimalFormat("#,##0.0000")
            val price = dec.format(coin.price)
            text = "$$price"
        }
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