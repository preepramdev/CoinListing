package com.maxnimal.coin.listing.presentation.coin.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxnimal.coin.listing.domain.model.CoinModel
import com.maxnimal.coin.listing.domain.usecase.GetCoinUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CoinDetailViewModel(
    private val getCoinUseCase: GetCoinUseCase
) : ViewModel() {

    // todo loading
    // todo error

    private val _showCoin = MutableLiveData<CoinModel>()
    val showCoin: LiveData<CoinModel> = _showCoin

    private var uuid: String? = null

    fun setUuid(uuid: String?) {
        this.uuid = uuid
    }

    fun getCoin() = viewModelScope.launch {
        uuid?.let { _uuid ->
            getCoinUseCase.execute(_uuid)
                .catch { exception ->
                    exception.printStackTrace()
                }.collect { coinModel ->
                    _showCoin.value = coinModel
                }
        } ?: run {
            // error null
        }
    }
}