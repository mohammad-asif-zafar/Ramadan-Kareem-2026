package com.hathway.ramadankareem2026.ui.prayer.data

import com.hathway.ramadankareem2026.ui.home.model.PrayerDomain
import java.time.LocalTime

object PrayerDemoData {

    fun get(): List<PrayerDomain> = listOf(
        PrayerDomain(
            name = "Fajr",
            time = LocalTime.of(5, 50),
            isCurrent = false,
            isNext = false,
            remainingMinutes = null
        ), PrayerDomain(
            name = "Dhuhr",
            time = LocalTime.of(13, 20),
            isCurrent = true,
            isNext = false,
            remainingMinutes = null
        ), PrayerDomain(
            name = "Asr",
            time = LocalTime.of(16, 45),
            isCurrent = false,
            isNext = true,
            remainingMinutes = 28
        ), PrayerDomain(
            name = "Maghrib",
            time = LocalTime.of(19, 22),
            isCurrent = false,
            isNext = false,
            remainingMinutes = null
        ), PrayerDomain(
            name = "Isha",
            time = LocalTime.of(20, 40),
            isCurrent = false,
            isNext = false,
            remainingMinutes = null
        )
    )
}
