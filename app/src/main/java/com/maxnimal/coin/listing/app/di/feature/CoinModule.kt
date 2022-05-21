package com.maxnimal.coin.listing.app.di.feature

import com.maxnimal.coin.listing.data.repository.CoinRepository
import com.maxnimal.coin.listing.data.repository.CoinRepositoryImpl
import com.maxnimal.coin.listing.domain.usecase.GetCoinUseCase
import com.maxnimal.coin.listing.domain.usecase.GetCoinUseCaseImpl
import com.maxnimal.coin.listing.domain.usecase.GetCoinsUseCase
import com.maxnimal.coin.listing.domain.usecase.GetCoinsUseCaseImpl
import com.maxnimal.coin.listing.presentation.ui.coin.detail.CoinDetailViewModel
import com.maxnimal.coin.listing.presentation.ui.coin.list.CoinListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val coinModule = module {

    single <CoinRepository> {
        CoinRepositoryImpl(
            coinRankingApiService = get()
        )
    }

    factory<GetCoinUseCase> {
        GetCoinUseCaseImpl(
            coinRepository = get()
        )
    }

    factory<GetCoinsUseCase> {
        GetCoinsUseCaseImpl(
            coinRepository = get()
        )
    }

    viewModel {
        CoinDetailViewModel(
            getCoinUseCase = get()
        )
    }

    viewModel {
        CoinListViewModel(
            getCoinsUseCase = get()
        )
    }
}