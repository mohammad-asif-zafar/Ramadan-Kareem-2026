package com.hathway.ramadankareem2026.ui.ramadan.model

import java.time.LocalDate
import java.time.LocalTime

enum class FastingDayStatus {
    UPCOMING, FASTING, TODAY, COMPLETED
}

/**
 * Represents current fasting status during Ramadan
 */
data class FastingState(
    val isFastingNow: Boolean, val nextEvent: String,      // Imsak / Fajr / Iftar
    val minutesRemaining: Int
)

data class RamadanDayUiModel(
    val ramadanDay: Int,
    val date: LocalDate,
    val weekday: String,
    val month: String,
    val imsak: LocalTime,
    val fajr: LocalTime,
    val maghrib: LocalTime,
    val status: FastingDayStatus,
    // Countdown based
    val totalMinutes: Int,
    val remainingMinutes: Int
) {
    val progress: Float
        get() = remainingMinutes / totalMinutes.toFloat()
}