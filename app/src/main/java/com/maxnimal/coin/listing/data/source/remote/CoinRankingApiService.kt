package com.maxnimal.coin.listing.data.source.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinRankingApiService {

    @GET("/coin/{uuid}")
    suspend fun getCoin(
        @Path("uuid") uuid: String
    )

    @GET("/coins")
    suspend fun getCoins(
        @Query("limit") limit: Int
    )

    @GET("/coins")
    suspend fun searchCoins(
        @Query("search") keyword: String
    )
}