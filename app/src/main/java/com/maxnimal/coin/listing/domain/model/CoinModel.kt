package com.maxnimal.coin.listing.domain.model

data class CoinModel(
    val uuid: String,
    val symbol: String,
    val name: String,
    val iconUrl: String,
    val color: String,
    val description: String,
    val websiteUrl: String,
    val price: Double,
    val change: Double,
    val marketCap: String,
)