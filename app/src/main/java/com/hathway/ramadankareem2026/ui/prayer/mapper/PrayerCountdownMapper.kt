package com.hathway.ramadankareem2026.ui.prayer.mapper

import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeUiState
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime

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

        val today = LocalDate.now()

        val next = prayers.map { it.first to LocalDateTime.of(today, it.second) }
            .firstOrNull { it.second.isAfter(now) }
            ?: ("Fajr" to LocalDateTime.of(today.plusDays(1), state.fajr))

        val duration = Duration.between(now, next.second)

        return CountdownResult(
            prayerName = next.first, minutes = duration.toMinutes().toInt()
        )
    }
}

data class CountdownResult(
    val prayerName: String, val minutes: Int
)
