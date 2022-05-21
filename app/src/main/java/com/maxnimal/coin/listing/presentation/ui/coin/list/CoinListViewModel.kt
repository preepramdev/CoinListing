package com.maxnimal.coin.listing.presentation.ui.coin.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxnimal.coin.listing.domain.model.CoinModel
import com.maxnimal.coin.listing.domain.usecase.GetCoinsUseCase
import com.maxnimal.coin.listing.presentation.common.SingleLiveEvent
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CoinListViewModel(
    private val getCoinsUseCase: GetCoinsUseCase
) : ViewModel() {

    companion object {
        private const val DEFAULT_LIMIT = 20
    }

    private var currentCoinList = mutableListOf<CoinModel>()

    private val _showCoinList = MutableLiveData<List<CoinModel>>()
    private val _showError = MutableLiveData<Unit>()
    private val _showErrorLoadMore = SingleLiveEvent<Unit>()
    private val _showLoading = MutableLiveData<Unit>()
    private val _hideLoading = MutableLiveData<Unit>()

    val showCoinList: LiveData<List<CoinModel>> = _showCoinList
    val showError: LiveData<Unit> = _showError
    val showErrorLoadMore: LiveData<Unit> = _showErrorLoadMore
    val showLoading: LiveData<Unit> = _showLoading
    val hideLoading: LiveData<Unit> = _hideLoading

    fun getCoins() = viewModelScope.launch {
        val currentOffset = currentCoinList.size
        getCoinsUseCase.execute(DEFAULT_LIMIT, currentOffset)
            .onStart {
                if (currentCoinList.isEmpty()) {
                    _showLoading.value = Unit
                }
            }
            .onCompletion {
                _hideLoading.value = Unit
            }
            .catch { exception ->
                exception.printStackTrace()
                if (currentCoinList.isEmpty()) {
                    _showError.value = Unit
                } else {
                    _showErrorLoadMore.value = Unit
                }
            }.collect { coins ->
                currentCoinList.addAll(coins)
                _showCoinList.value = currentCoinList
            }
    }

    fun reloadCoins() {
        currentCoinList.clear()
        _showCoinList.value = currentCoinList
        getCoins()
    }

    fun updateCoins() = viewModelScope.launch {
        val refreshLimit = currentCoinList.size
        if (refreshLimit > 0) {
            getCoinsUseCase.execute(refreshLimit, 0)
                .catch { exception ->
                    exception.printStackTrace()
                }.collect { coins ->
                    currentCoinList = coins.toMutableList()
                    _showCoinList.value = currentCoinList
                }
        }
    }
}