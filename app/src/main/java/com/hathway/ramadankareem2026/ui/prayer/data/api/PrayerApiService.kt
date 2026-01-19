package com.hathway.ramadankareem2026.ui.prayer.data.api

import com.hathway.ramadankareem2026.ui.prayer.data.model.PrayerApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PrayerApiService {

    @GET("v1/timings/{date}")
    suspend fun getTimings(
        @Path("date") date: String,
        @Query("latitude") lat: Double,
        @Query("longitude") lng: Double,
        @Query("method") method: Int = 3,
        @Query("timezonestring") timezone: String = "Asia/Kuala_Lumpur",
        @Query("calendarMethod") calendarMethod: String = "UAQ"
    ): PrayerApiResponse
}
