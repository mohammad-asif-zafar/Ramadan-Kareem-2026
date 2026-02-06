package com.hathway.ramadankareem2026.ui.prayer

import com.hathway.ramadankareem2026.ui.home.model.PrayerDomain
import java.time.Duration
import java.time.LocalTime

object PrayerTimeUiMapper {

    fun map(
        state: PrayerTimeUiState, now: LocalTime = LocalTime.now()
    ): List<PrayerDomain> {

        val prayers = listOf(
            PrayerType.FAJR to state.fajr,
            PrayerType.DHUHR to state.dhuhr,
            PrayerType.ASR to state.asr,
            PrayerType.MAGHRIB to state.maghrib,
            PrayerType.ISHA to state.isha
        )

        val nextIndex = prayers.indexOfFirst { (_, time) ->
            time.isAfter(now)
        }

        val currentIndex = when {
            nextIndex > 0 -> nextIndex - 1
            nextIndex == -1 -> prayers.lastIndex
            else -> 0
        }

        val nextPrayerTime = if (nextIndex == -1) state.fajr.plusHours(24)
        else prayers[nextIndex].second

        val remainingMinutes = Duration.between(now, nextPrayerTime).toMinutes().toInt()

        return prayers.mapIndexed { index, (type, time) ->

            val isPast = when {
                index < currentIndex -> true
                currentIndex == prayers.lastIndex && index != currentIndex -> true
                else -> false
            }

            PrayerDomain(
                type = type,
                time = time,
                isCurrent = index == currentIndex,
                isNext = index == nextIndex,
                remainingMinutes = if (index == nextIndex) remainingMinutes else null,
                isPast = isPast
            )
        }
    }
}

enum class PrayerType {
    FAJR, DHUHR, ASR, MAGHRIB, ISHA
}
