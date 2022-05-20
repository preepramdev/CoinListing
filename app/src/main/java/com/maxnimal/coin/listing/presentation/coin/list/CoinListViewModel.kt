package com.maxnimal.coin.listing.presentation.coin.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxnimal.coin.listing.data.source.remote.CoinRankingApiService
import com.maxnimal.coin.listing.domain.model.CoinModel
import com.maxnimal.coin.listing.domain.usecase.GetCoinsUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CoinListViewModel(
    private val getCoinsUseCase: GetCoinsUseCase
) : ViewModel() {

    // todo current list size
    // todo current offset
    // todo auto update every 10 sec

    private val _showCoinList = MutableLiveData<List<CoinModel>>()
    private var currentOffset = 0

    val showCoinList: LiveData<List<CoinModel>> = _showCoinList

    fun getCoins() {
        viewModelScope.launch {
            getCoinsUseCase.execute(currentOffset).catch { exception ->
                exception.printStackTrace()
            }.collect { coins ->
                _showCoinList.value = coins
                currentOffset += coins.size
            }
        }
    }
}