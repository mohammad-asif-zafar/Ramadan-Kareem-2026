package com.hathway.ramadankareem2026.ui.mosques.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PlacesApiProvider {

    fun create(): PlacesApi =
        Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PlacesApi::class.java)
}
