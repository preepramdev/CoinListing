package com.maxnimal.coin.listing.app.di

import com.maxnimal.coin.listing.app.di.feature.coinModule
import com.maxnimal.coin.listing.app.di.shared.networkModule

val appModule = listOf(coinModule, networkModule)