package com.hathway.ramadankareem2026.ui.mosques.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface PlacesApi {

    @GET("maps/api/place/nearbysearch/json")
    suspend fun nearbyMosques(
        @Query("location") location: String,
        @Query("radius") radius: Int = 3000,
        @Query("type") type: String = "mosque",
        @Query("keyword") keyword: String = "mosque",
        @Query("key") apiKey: String
    ): PlacesResponse
}
