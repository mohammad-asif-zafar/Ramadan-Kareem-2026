package com.hathway.ramadankareem2026.ui.prayer.data.model

/**
 * Prayer timings from API (String format)
 */
data class PrayerTimingsDto(
    val Fajr: String,
    val Sunrise: String,
    val Dhuhr: String,
    val Asr: String,
    val Maghrib: String,
    val Isha: String,
    val Imsak: String,
    val Midnight: String
)
