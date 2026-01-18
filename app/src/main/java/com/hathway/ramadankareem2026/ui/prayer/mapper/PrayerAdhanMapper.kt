package com.hathway.ramadankareem2026.ui.prayer.mapper

import com.batoulapps.adhan.Prayer
import com.batoulapps.adhan.PrayerTimes
import com.hathway.ramadankareem2026.ui.home.model.PrayerDomain
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId

object PrayerAdhanMapper {

    fun map(
        prayerTimes: PrayerTimes,
        now: LocalDateTime
    ): List<PrayerDomain> {

        val currentPrayer = prayerTimes.currentPrayer()
        val nextPrayer = prayerTimes.nextPrayer()
        val zoneId = ZoneId.systemDefault()

        return Prayer.values()
            .filter { it != Prayer.NONE }
            .mapNotNull { prayer ->

                val time = prayerTimes.timeForPrayer(prayer) ?: return@mapNotNull null

                // ✅ CORRECT CONVERSION
                val localTime = time.toInstant()
                    .atZone(zoneId)
                    .toLocalTime()

                val remainingMinutes =
                    if (prayer == nextPrayer)
                        Duration.between(
                            now.toLocalTime(),
                            localTime
                        ).toMinutes().toInt()
                    else null

                PrayerDomain(
                    name = prayer.name
                        .lowercase()
                        .replaceFirstChar { it.uppercase() },
                    time = localTime,
                    isCurrent = prayer == currentPrayer,
                    isNext = prayer == nextPrayer,
                    remainingMinutes = remainingMinutes
                )
            }
    }
}