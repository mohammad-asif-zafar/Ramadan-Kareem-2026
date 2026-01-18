package com.hathway.ramadankareem2026.ui.prayer


import java.time.LocalDate
import java.time.LocalTime
import java.time.chrono.HijrahDate

/**
 * PrayerTimeUiState
 *
 * SINGLE SOURCE OF TRUTH for prayer-related data.
 *
 * This model is used by:
 * - Prayer UI
 * - Countdown logic
 * - API mapping
 * - Local caching
 */
data class PrayerTimeUiState(

    // 🕰️ Prayer times (local device time)
    val fajr: LocalTime,
    val sunrise: LocalTime,
    val dhuhr: LocalTime,
    val asr: LocalTime,
    val maghrib: LocalTime,
    val isha: LocalTime,

    // 📅 Gregorian date (used for countdown & day calculations)
    val gregorianDate: LocalDate,

    // 🌙 Hijri date (display-friendly string)
    // Example: "01 Rajab 1446 AH"
    val hijriDate: String   // ✅ STRING

)
