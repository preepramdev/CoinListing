package com.maxnimal.coin.listing.app.network

import com.maxnimal.coin.listing.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticatorInterceptor : Interceptor {

    companion object {
        private const val HEADER_ACCESS_X_TOKEN = "x-access-token"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader(HEADER_ACCESS_X_TOKEN, BuildConfig.API_KEY)
        return chain.proceed(requestBuilder.build())
    }
}