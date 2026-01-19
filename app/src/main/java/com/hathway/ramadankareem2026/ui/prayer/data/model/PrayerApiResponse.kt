package com.hathway.ramadankareem2026.ui.prayer.data.model


/**
 * Root response from Prayer API
 */
data class PrayerApiResponse(
    val code: Int,
    val status: String,
    val data: PrayerApiData
)

/**
 * Main data payload
 */
data class PrayerApiData(
    val timings: PrayerTimingsDto,
    val date: PrayerDateDto
)
