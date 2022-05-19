package com.maxnimal.coin.listing.app.di.shared

import com.google.gson.Gson
import com.maxnimal.coin.listing.BuildConfig
import com.maxnimal.coin.listing.data.source.remote.CoinRankingApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single {
        Gson()
    }

    single {
        HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    single {
        provideOkHttpClient(
            httpLoggingInterceptor = get()
        )
    }

    single {
        provideRetrofit(
            okHttpClient = get()
        )
    }

    single {
        provideCoinRankingApiService(
            retrofit = get()
        )
    }
}

fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

fun provideCoinRankingApiService(retrofit: Retrofit): CoinRankingApiService =
    retrofit.create(CoinRankingApiService::class.java)