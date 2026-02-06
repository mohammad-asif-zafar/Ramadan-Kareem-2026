package com.hathway.ramadankareem2026.ui.prayer.data

import com.hathway.ramadankareem2026.ui.home.model.PrayerDomain
import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeUiState
import com.hathway.ramadankareem2026.ui.prayer.PrayerType
import java.time.LocalDate
import java.time.LocalTime

/**
 * Demo / fallback prayer data
 *
 * Used when:
 * - App starts
 * - No internet
 * - API fails
 *
 * IMPORTANT:
 * Demo data MUST follow the same rules as real data.
 */
object PrayerDemoData {

    /**
     * Demo prayer list for UI (horizontal cards)
     *
     * Scenario:
     * - Dhuhr is current
     * - Asr is next
     */
    fun get(): List<PrayerDomain> = listOf(
        PrayerDomain(
            type = PrayerType.FAJR,
            time = LocalTime.of(5, 50),
            isCurrent = false,
            isNext = false,
            remainingMinutes = null,
            isPast = true
        ), PrayerDomain(
            type = PrayerType.DHUHR,
            time = LocalTime.of(13, 20),
            isCurrent = true,
            isNext = false,
            remainingMinutes = null,
            isPast = false
        ), PrayerDomain(
            type = PrayerType.ASR,
            time = LocalTime.of(16, 45),
            isCurrent = false,
            isNext = true,
            remainingMinutes = 28,
            isPast = false
        ), PrayerDomain(
            type = PrayerType.MAGHRIB,
            time = LocalTime.of(19, 22),
            isCurrent = false,
            isNext = false,
            remainingMinutes = null,
            isPast = false
        ), PrayerDomain(
            type = PrayerType.ISHA,
            time = LocalTime.of(20, 40),
            isCurrent = false,
            isNext = false,
            remainingMinutes = null,
            isPast = false
        )
    )

    /**
     * Demo PrayerTimeUiState
     *
     * Used as initial / fallback state
     */
    fun demo(): PrayerTimeUiState = PrayerTimeUiState(
        fajr = LocalTime.of(6, 0),
        sunrise = LocalTime.of(7, 15),
        dhuhr = LocalTime.of(12, 30),
        asr = LocalTime.of(15, 45),
        maghrib = LocalTime.of(18, 10),
        isha = LocalTime.of(19, 30),
        gregorianDate = LocalDate.now(),
        hijriDate = "1 Ramadan 1447 AH"
    )
}
