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
        private const val LIMIT = 20
        private const val OFFSET = 0
    }

    override fun execute(offset: Int): Flow<List<CoinModel>> {
        return coinRepository.getCoins(LIMIT, offset).flatMapConcat { getCoinsResponse ->
            if (getCoinsResponse.status == STATUS_SUCCESS) {
                flowOf(mapToCoinModelList(getCoinsResponse.data?.coins))
            } else {
                error("getCoinsResponse fail")
            }

        }
    }

    private fun mapToCoinModelList(coins: List<GetCoinsResponse.Coin>?): List<CoinModel> {
        return coins?.let { _coins ->
            _coins.map { coin ->
                CoinModel(
                    uuid = coin.uuid.orEmpty(),
                    symbol = coin.symbol.orEmpty(),
                    name = coin.name.orEmpty(),
                    iconUrl = coin.iconUrl.orEmpty(),
                    color = coin.color.orEmpty(),
                    description = "",
                    websiteUrl = "",
                    price = coin.price?.toDouble() ?: 0.0,
                    change = coin.change?.toDouble() ?: 0.0,
                    marketCap = coin.marketCap.orEmpty()
                )
            }
        } ?: run {
            listOf()
        }
    }
}