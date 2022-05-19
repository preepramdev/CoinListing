package com.maxnimal.coin.listing.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GetCoinsResponse(
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("data")
    val data: Data? = null
) {
    data class Data(
        @SerializedName("stats")
        val stats: Stats? = null,
        @SerializedName("coins")
        val coins: List<Coin>? = null
    )

    data class Stats(
        @SerializedName("total")
        val total: String? = null,
        @SerializedName("totalCoins")
        val totalCoins: String? = null,
        @SerializedName("totalMarkets")
        val totalMarkets: String? = null,
        @SerializedName("totalExchanges")
        val totalExchanges: String? = null,
        @SerializedName("totalMarketCap")
        val totalMarketCap: String? = null,
        @SerializedName("total24hVolume")
        val total24hVolume: String? = null
    )

    data class Coin(
        @SerializedName("uuid")
        val uuid: String? = null,
        @SerializedName("symbol")
        val symbol: String? = null,
        @SerializedName("name")
        val name: String? = null,
        @SerializedName("color")
        val color: String? = null,
        @SerializedName("iconUrl")
        val iconUrl: String? = null,
        @SerializedName("marketCap")
        val marketCap: String? = null,
        @SerializedName("price")
        val price: String? = null,
        @SerializedName("btcPrice")
        val btcPrice: String? = null,
        @SerializedName("listedAt")
        val listedAt: String? = null,
        @SerializedName("change")
        val change: String? = null,
        @SerializedName("rank")
        val rank: String? = null,
        @SerializedName("coinrankingUrl")
        val coinRankingUrl: String? = null,
        @SerializedName("24hVolume")
        val volume24H: String? = null,
        @SerializedName("sparkline")
        val sparkline: List<String>? = null
    )
}
