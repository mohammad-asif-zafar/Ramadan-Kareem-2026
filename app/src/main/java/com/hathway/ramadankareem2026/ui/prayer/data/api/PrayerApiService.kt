package com.hathway.ramadankareem2026.ui.prayer.data.api

import com.hathway.ramadankareem2026.ui.prayer.data.model.AladhanCalendarResponse
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
        @Query("timezone") timezone: String? = null,
        @Query("calendarMethod") calendarMethod: String = "UAQ"
    ): PrayerApiResponse

    @GET("v1/gToHCalendar/{month}/{year}")
    suspend fun getGregorianToHijriCalendar(
        @Path("month") month: Int,
        @Path("year") year: Int
    ): AladhanCalendarResponse

    @GET("v1/hToGCalendar/{month}/{year}")
    suspend fun getHijriToGregorianCalendar(
        @Path("month") month: Int,
        @Path("year") year: Int
    ): AladhanCalendarResponse
}
