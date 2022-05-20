package com.maxnimal.coin.listing.domain.usecase

import android.util.Log
import com.maxnimal.coin.listing.data.repository.CoinRepository
import com.maxnimal.coin.listing.data.source.remote.response.GetCoinsResponse
import com.maxnimal.coin.listing.domain.model.CoinModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf

interface GetCoinsUseCase {

    fun execute(offset: Int): Flow<List<CoinModel>>
}

class GetCoinsUseCaseImpl(
    private val coinRepository: CoinRepository
) : GetCoinsUseCase {

    companion object {
        private const val STATUS_SUCCESS = "success"
        private const val LIMIT = 10
        private const val OFFSET = 0
    }

    override fun execute(offset: Int): Flow<List<CoinModel>> {
        return coinRepository.getCoins(LIMIT, offset).flatMapConcat { getCoinsResponse ->
            getCoinsResponse?.let { _getCoinsResponse ->
                if (_getCoinsResponse.status == STATUS_SUCCESS) {
                    flowOf(mapToCoinModelList(_getCoinsResponse.data?.coins))
                } else {
                    error("getCoinsResponse fail")
                }

            } ?: run {
                error("getCoinsResponse null")
            }
        }
    }

    private fun mapToCoinModelList(coins: List<GetCoinsResponse.Coin>?): List<CoinModel> {
        return coins?.let { _coins ->
            Log.e("TAG", "mapToCoinModelList: ${coins.size}")
            _coins.map { coin ->
                CoinModel(
                    uuid = coin.uuid,
                    symbol = coin.symbol,
                    name = coin.name,
                    iconUrl = coin.iconUrl
                )
            }
        } ?: run {
            Log.e("TAG", "mapToCoinModelList: null")
            listOf()
        }
    }
}