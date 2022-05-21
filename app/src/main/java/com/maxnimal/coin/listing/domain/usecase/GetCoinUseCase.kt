package com.maxnimal.coin.listing.domain.usecase

import android.util.Log
import com.maxnimal.coin.listing.data.repository.CoinRepository
import com.maxnimal.coin.listing.data.source.remote.response.GetCoinResponse
import com.maxnimal.coin.listing.domain.model.CoinModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf

interface GetCoinUseCase {

    fun execute(uuid: String): Flow<CoinModel?>
}

class GetCoinUseCaseImpl(
    private val coinRepository: CoinRepository
) : GetCoinUseCase {

    companion object {
        private const val DEFAULT_PRICE = 0.0
        private const val DEFAULT_CHANGE = 0.0
        private const val STATUS_SUCCESS = "success"
    }

    override fun execute(uuid: String): Flow<CoinModel?> {
        return coinRepository.getCoin(uuid).flatMapConcat { getCoinResponse ->
            if (getCoinResponse.status == STATUS_SUCCESS) {
                flowOf(mapToCoinModel(getCoinResponse.data?.coin))
            } else {
                error("getCoinResponse fail")
            }

        }
    }

    private fun mapToCoinModel(coin: GetCoinResponse.Coin?): CoinModel? {
        return coin?.let { _coin ->
            CoinModel(
                uuid = _coin.uuid.orEmpty(),
                symbol = _coin.symbol.orEmpty(),
                name = _coin.name.orEmpty(),
                iconUrl = _coin.iconUrl.orEmpty(),
                color = _coin.color.orEmpty(),
                description = _coin.description.orEmpty(),
                price = coin.price?.toDouble() ?: DEFAULT_PRICE,
                change = coin.change?.toDouble() ?: DEFAULT_CHANGE,
                marketCap = _coin.marketCap.orEmpty(),
                websiteUrl = _coin.websiteUrl.orEmpty()
            )
        } ?: run {
            null
        }
    }
}