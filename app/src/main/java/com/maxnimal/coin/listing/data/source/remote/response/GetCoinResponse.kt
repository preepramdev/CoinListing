package com.maxnimal.coin.listing.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GetCoinResponse(
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("data")
    val data: Data? = null

) {
    data class Data(
        @SerializedName("coin")
        val coin: Coin
    )

    data class Coin(
        @SerializedName("uuid")
        val uuid: String? = null,
        @SerializedName("symbol")
        val symbol: String? = null,
        @SerializedName("name")
        val name: String? = null,
        @SerializedName("description")
        val description: String? = null,
        @SerializedName("color")
        val color: String? = null,
        @SerializedName("iconUrl")
        val iconUrl: String? = null,
        @SerializedName("websiteUrl")
        val websiteUrl: String? = null,
        @SerializedName("links")
        val links: List<Link>? = null,
        @SerializedName("supply")
        val supply: Supply,
        @SerializedName("numberOfMarkets")
        val numberOfMarkets: String? = null,
        @SerializedName("numberOfExchanges")
        val numberOfExchanges: String? = null,
        @SerializedName("24hVolume")
        val volume24H: String? = null,
        @SerializedName("marketCap")
        val marketCap: String? = null,
        @SerializedName("price")
        val price: String? = null,
        @SerializedName("btcPrice")
        val btcPrice: String? = null,
        @SerializedName("priceAt")
        val priceAt: String? = null,
        @SerializedName("change")
        val change: String? = null,
        @SerializedName("rank")
        val rank: String? = null,
        @SerializedName("sparkline")
        val sparkline: List<String>? = null,
        @SerializedName("allTimeHigh")
        val allTimeHigh: AllTimeHigh? = null,
        @SerializedName("coinrankingUrl")
        val coinRankingUrl: String? = null,
        @SerializedName("tier")
        val tier: String? = null,
        @SerializedName("lowVolume")
        val lowVolume: Boolean? = null,
        @SerializedName("listedAt")
        val listedAt: String? = null
    )

    data class Link(
        @SerializedName("name")
        val name: String? = null,
        @SerializedName("type")
        val type: String? = null,
        @SerializedName("url")
        val url: String? = null
    )

    data class Supply(
        @SerializedName("confirmed")
        val confirmed: Boolean? = null,
        @SerializedName("type")
        val total: String? = null,
        @SerializedName("url")
        val circulating: String? = null
    )

    data class AllTimeHigh(
        @SerializedName("price")
        val price: Boolean? = null,
        @SerializedName("timestamp")
        val timestamp: String? = null
    )
}
