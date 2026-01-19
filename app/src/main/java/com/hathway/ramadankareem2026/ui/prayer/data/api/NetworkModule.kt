package com.hathway.ramadankareem2026.ui.prayer.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {

    val prayerApi: PrayerApiService by lazy {
        Retrofit.Builder().baseUrl("https://api.aladhan.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(PrayerApiService::class.java)
    }
}