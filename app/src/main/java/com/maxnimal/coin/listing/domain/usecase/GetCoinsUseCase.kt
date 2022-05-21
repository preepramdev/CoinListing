package com.maxnimal.coin.listing.domain.usecase

import com.maxnimal.coin.listing.data.repository.CoinRepository
import com.maxnimal.coin.listing.data.source.remote.response.GetCoinsResponse
import com.maxnimal.coin.listing.domain.model.CoinModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf

interface GetCoinsUseCase {

    fun execute(
        limit: Int,
        offset: Int
    ): Flow<List<CoinModel>>
}

class GetCoinsUseCaseImpl(
    private val coinRepository: CoinRepository
) : GetCoinsUseCase {

    companion object {
        private const val DEFAULT_PRICE = 0.0
        private const val DEFAULT_CHANGE = 0.0
        private const val EMPTY_STRING = ""
        private const val STATUS_SUCCESS = "success"
    }

    override fun execute(limit: Int, offset: Int): Flow<List<CoinModel>> {
        return coinRepository.getCoins(limit, offset).flatMapConcat { getCoinsResponse ->
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
                    description = EMPTY_STRING,
                    websiteUrl = EMPTY_STRING,
                    price = coin.price?.toDouble() ?: DEFAULT_PRICE,
                    change = coin.change?.toDouble() ?: DEFAULT_CHANGE,
                    marketCap = coin.marketCap.orEmpty()
                )
            }
        } ?: run {
            listOf()
        }
    }
}