package com.hathway.ramadankareem2026.ui.prayer.mapper

import com.batoulapps.adhan.Prayer
import com.batoulapps.adhan.PrayerTimes
import com.hathway.ramadankareem2026.ui.home.model.PrayerDomain
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId

object PrayerAdhanMapper {

    fun map(
        prayerTimes: PrayerTimes, now: LocalDateTime
    ): List<PrayerDomain> {

        val currentPrayer = prayerTimes.currentPrayer()
        val nextPrayer = prayerTimes.nextPrayer()
        val zoneId = ZoneId.systemDefault()

        // Build ordered list of prayers with times
        val prayers = Prayer.entries.filter { it != Prayer.NONE }.mapNotNull { prayer ->
            val date = prayerTimes.timeForPrayer(prayer) ?: return@mapNotNull null
            val localTime = date.toInstant().atZone(zoneId).toLocalTime()
            prayer to localTime
        }

        return prayers.mapIndexed { index, (prayer, localTime) ->

            val isCurrent = prayer == currentPrayer
            val isNext = prayer == nextPrayer

            // ✅ Explicit past calculation
            val isPast = when {
                isCurrent || isNext -> false
                currentPrayer == Prayer.NONE -> false
                else -> {
                    val currentIndex = prayers.indexOfFirst { it.first == currentPrayer }
                    currentIndex != -1 && index < currentIndex
                }
            }

            val remainingMinutes = if (isNext) Duration.between(
                now.toLocalTime(), localTime
            ).toMinutes().toInt()
            else null

            PrayerDomain(
                name = prayer.name.lowercase().replaceFirstChar { it.uppercase() },
                time = localTime,
                isCurrent = isCurrent,
                isNext = isNext,
                remainingMinutes = remainingMinutes,
                isPast = isPast          // ✅ CRITICAL FIX
            )


        }
    }
}