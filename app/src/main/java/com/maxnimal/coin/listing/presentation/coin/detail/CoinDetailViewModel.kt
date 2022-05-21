package com.maxnimal.coin.listing.presentation.coin.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxnimal.coin.listing.domain.model.CoinModel
import com.maxnimal.coin.listing.domain.usecase.GetCoinUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CoinDetailViewModel(
    private val getCoinUseCase: GetCoinUseCase
) : ViewModel() {

    private val _showCoin = MutableLiveData<CoinModel>()
    private val _showError = MutableLiveData<Unit>()
    private val _showErrorUuid = MutableLiveData<Unit>()
    private val _showLoading = MutableLiveData<Unit>()
    private val _hideLoading = MutableLiveData<Unit>()

    val showCoin: LiveData<CoinModel> = _showCoin
    val showError: LiveData<Unit> = _showError
    val showErrorUuid: LiveData<Unit> = _showErrorUuid
    val showLoading: LiveData<Unit> = _showLoading
    val hideLoading: LiveData<Unit> = _hideLoading

    private var uuid: String? = null

    fun setUuid(uuid: String?) {
        this.uuid = uuid
    }

    fun getCoin() = viewModelScope.launch {
        uuid?.let { _uuid ->
            getCoinUseCase.execute(_uuid)
                .onStart {
                    _showLoading.value = Unit
                }.onCompletion {
                    _hideLoading.value = Unit
                }
                .catch { exception ->
                    exception.printStackTrace()
                    _showError.value = Unit
                }.collect { coinModel ->
                    _showCoin.value = coinModel
                }
        } ?: run {
            _showErrorUuid.value = Unit
        }
    }
}