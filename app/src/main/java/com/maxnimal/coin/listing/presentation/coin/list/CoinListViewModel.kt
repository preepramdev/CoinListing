package com.maxnimal.coin.listing.presentation.coin.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxnimal.coin.listing.domain.model.CoinModel
import com.maxnimal.coin.listing.domain.usecase.GetCoinsUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CoinListViewModel(
    private val getCoinsUseCase: GetCoinsUseCase
) : ViewModel() {

    companion object {
        private const val DEFAULT_LIMIT = 20
    }

    private val _showCoinList = MutableLiveData<List<CoinModel>>()
    private var currentCoinList = mutableListOf<CoinModel>()

    val showCoinList: LiveData<List<CoinModel>> = _showCoinList

    fun getCoins() = viewModelScope.launch {
        val currentOffset = currentCoinList.size
        getCoinsUseCase.execute(DEFAULT_LIMIT, currentOffset).catch { exception ->
            exception.printStackTrace()
        }.collect { coins ->
            currentCoinList.addAll(coins)
            _showCoinList.value = currentCoinList
        }
    }

    fun refreshCoins() = viewModelScope.launch {
        val refreshLimit = currentCoinList.size
        getCoinsUseCase.execute(refreshLimit, 0).catch { exception ->
            exception.printStackTrace()
        }.collect { coins ->
            currentCoinList = coins.toMutableList()
            _showCoinList.value = currentCoinList
        }
    }
}