package com.hathway.ramadankareem2026.core.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkClient {

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .build()
    }

    /**
     * Create Retrofit service with dynamic baseUrl
     */
    fun <T> create(
        service: Class<T>,
        baseUrl: String
    ): T {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(service)
    }
}

