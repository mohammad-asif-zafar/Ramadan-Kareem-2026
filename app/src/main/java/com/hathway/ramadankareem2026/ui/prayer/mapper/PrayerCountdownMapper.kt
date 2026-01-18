package com.hathway.ramadankareem2026.ui.prayer.mapper


import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeUiState
import java.time.Duration
import java.time.LocalDateTime

/**
 * Calculates countdown to next prayer
 */
object PrayerCountdownMapper {

    fun nextPrayerCountdown(
        state: PrayerTimeUiState,
        now: LocalDateTime = LocalDateTime.now()
    ): CountdownResult {

        val prayers = listOf(
            "Fajr" to state.fajr,
            "Dhuhr" to state.dhuhr,
            "Asr" to state.asr,
            "Maghrib" to state.maghrib,
            "Isha" to state.isha
        )

        val today = state.gregorianDate

        val next = prayers
            .map { it.first to LocalDateTime.of(today, it.second) }
            .firstOrNull { it.second.isAfter(now) }
            ?: ("Fajr" to LocalDateTime.of(today.plusDays(1), state.fajr))

        val duration = Duration.between(now, next.second)

        return CountdownResult(
            prayerName = next.first,
            minutesRemaining = duration.toMinutes().toInt()
        )
    }
}
