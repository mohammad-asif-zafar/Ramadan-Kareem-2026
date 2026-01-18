package com.hathway.ramadankareem2026.ui.prayer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import com.hathway.ramadankareem2026.ui.home.model.PrayerDomain
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object PrayerTimeUiMapper {

    private val formatter = DateTimeFormatter.ofPattern("hh:mm a")

    fun map(
        state: PrayerTimeUiState,
        now: LocalTime = LocalTime.now()
    ): List<PrayerDomain> {

        val prayers = listOf(
            "Fajr" to state.fajr,
            "Dhuhr" to state.dhuhr,
            "Asr" to state.asr,
            "Maghrib" to state.maghrib,
            "Isha" to state.isha
        )

        val nextIndex = prayers.indexOfFirst { (_, time) ->
            time.isAfter(now)
        }

        val currentIndex = when {
            nextIndex > 0 -> nextIndex - 1
            nextIndex == -1 -> prayers.lastIndex // after Isha
            else -> 0
        }

        val nextPrayerTime = if (nextIndex == -1) {
            state.fajr.plusHours(24) // tomorrow fajr
        } else {
            prayers[nextIndex].second
        }

        val remainingMinutes =
            Duration.between(now, nextPrayerTime).toMinutes().toInt()

        return prayers.mapIndexed { index, (name, time) ->
            PrayerDomain(
                name = name,
                time = time, // ✅ LocalTime ONLY

                isCurrent = index == currentIndex,
                isNext = index == nextIndex,
                remainingMinutes = if (index == nextIndex) remainingMinutes else null
            )
        }
    }


    private fun iconFor(name: String) = when (name) {
        "Fajr" -> Icons.Outlined.WbTwilight
        "Dhuhr" -> Icons.Outlined.LightMode
        "Asr" -> Icons.Outlined.WbSunny
        "Maghrib" -> Icons.Outlined.NightsStay
        "Isha" -> Icons.Outlined.DarkMode
        else -> Icons.Outlined.AccessTime
    }

    // 🔹 UI helper
    // 🔹 UI helper (FINAL)
    fun formatRemaining(
        minutes: Int?,
        isCurrent: Boolean
    ): String = when {
        minutes == null -> "Calculating…"
        minutes < 0 -> "Passed"
        isCurrent -> "${formatDuration(minutes)} left"
        else -> "Starts in ${formatDuration(minutes)}"
    }


    private fun formatDuration(minutes: Int): String {
        val h = minutes / 60
        val m = minutes % 60
        return if (h > 0) "${h}h ${m}m" else "${m}m"
    }
}
