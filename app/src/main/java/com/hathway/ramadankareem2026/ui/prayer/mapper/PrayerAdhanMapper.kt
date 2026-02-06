package com.hathway.ramadankareem2026.ui.prayer.mapper

import com.batoulapps.adhan.Prayer
import com.batoulapps.adhan.PrayerTimes
import com.hathway.ramadankareem2026.ui.home.model.PrayerDomain
import com.hathway.ramadankareem2026.ui.prayer.PrayerType
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

        val prayers = Prayer.entries.filter { it != Prayer.NONE }.mapNotNull { prayer ->
            val date = prayerTimes.timeForPrayer(prayer) ?: return@mapNotNull null
            prayer to date.toInstant().atZone(zoneId).toLocalTime()
        }

        return prayers.mapIndexed { index, (adhanPrayer, localTime) ->

            val type = adhanPrayer.toPrayerType()

            val isCurrent = adhanPrayer == currentPrayer
            val isNext = adhanPrayer == nextPrayer

            val isPast = when {
                isCurrent || isNext -> false
                currentPrayer == Prayer.NONE -> false
                else -> {
                    val currentIndex = prayers.indexOfFirst { it.first == currentPrayer }
                    currentIndex != -1 && index < currentIndex
                }
            }

            val remainingMinutes =
                if (isNext) Duration.between(now.toLocalTime(), localTime).toMinutes().toInt()
                else null

            PrayerDomain(
                type = type,
                time = localTime,
                isCurrent = isCurrent,
                isNext = isNext,
                remainingMinutes = remainingMinutes,
                isPast = isPast
            )
        }
    }
}

private fun Prayer.toPrayerType(): PrayerType = when (this) {
    Prayer.FAJR -> PrayerType.FAJR
    Prayer.DHUHR -> PrayerType.DHUHR
    Prayer.ASR -> PrayerType.ASR
    Prayer.MAGHRIB -> PrayerType.MAGHRIB
    Prayer.ISHA -> PrayerType.ISHA
    else -> error("Unsupported prayer: $this")
}
