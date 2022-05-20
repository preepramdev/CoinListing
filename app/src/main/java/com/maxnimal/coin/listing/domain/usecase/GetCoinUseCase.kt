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
                uuid = _coin.uuid,
                symbol = _coin.symbol,
                name = _coin.name,
                iconUrl = _coin.iconUrl,
                color = _coin.color,
                description = _coin.description,
                price = _coin.price,
                marketCap = _coin.marketCap,
                websiteUrl = _coin.websiteUrl
            )
        } ?: run {
            null
        }
    }
}