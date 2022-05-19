package com.maxnimal.coin.listing.presentation.coin.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxnimal.coin.listing.data.source.remote.CoinRankingApiService
import com.maxnimal.coin.listing.domain.usecase.GetCoinsUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CoinListViewModel(
    private val getCoinsUseCase: GetCoinsUseCase
) : ViewModel() {

    fun getCoins() {
        viewModelScope.launch {
            getCoinsUseCase.execute().catch { exception ->
                exception.printStackTrace()
            }.collect { coins ->
                Log.e("TAG", "getCoins: ${coins.size}")
            }
        }
    }
}