package com.hathway.ramadankareem2026.ui.prayer

import java.time.LocalTime

data class PrayerTimeUiState(
    val fajr: LocalTime,
    val sunrise: LocalTime,
    val dhuhr: LocalTime,
    val asr: LocalTime,
    val maghrib: LocalTime,
    val isha: LocalTime
)
