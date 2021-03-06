package com.maxnimal.coin.listing.data.repository

import com.maxnimal.coin.listing.data.source.remote.CoinRankingApiService
import com.maxnimal.coin.listing.data.source.remote.response.GetCoinResponse
import com.maxnimal.coin.listing.data.source.remote.response.GetCoinsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.HttpException

interface CoinRepository {

    fun getCoin(uuid: String): Flow<GetCoinResponse>
    fun getCoins(limit: Int, offset: Int?): Flow<GetCoinsResponse>
    fun searchCoins(keyword: String): Flow<GetCoinsResponse?>
}

class CoinRepositoryImpl(
    private val coinRankingApiService: CoinRankingApiService
) : CoinRepository {
    override fun getCoin(uuid: String): Flow<GetCoinResponse> = flow {
        val response = coinRankingApiService.getCoin(uuid)
        if (response.isSuccessful) {
            val getCoinResponse = response.body()
            if (getCoinResponse != null) {
                emit(getCoinResponse)
            } else {
                throw HttpException(response)
            }
        } else {
            throw HttpException(response)
        }
    }

    override fun getCoins(limit: Int, offset: Int?): Flow<GetCoinsResponse> = flow {
        val response = coinRankingApiService.getCoins(limit, offset)
        if (response.isSuccessful) {
            val getCoinsResponse = response.body()
            if (getCoinsResponse != null) {
                emit(getCoinsResponse)
            } else {
                throw HttpException(response)
            }
        } else {
            throw HttpException(response)
        }
    }

    override fun searchCoins(keyword: String): Flow<GetCoinsResponse> = flow {

    }

}