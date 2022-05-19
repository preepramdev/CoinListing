package com.maxnimal.coin.listing.app.di.feature

import com.maxnimal.coin.listing.presentation.coin.detail.CoinDetailViewModel
import com.maxnimal.coin.listing.presentation.coin.list.CoinListViewModel
import com.maxnimal.coin.listing.presentation.coin.toprank.TopRankCoinsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val coinModule = module {

    viewModel {
        CoinDetailViewModel()
    }

    viewModel {
        CoinListViewModel()
    }

    viewModel {
        TopRankCoinsViewModel()
    }
}