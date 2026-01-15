package com.hathway.ramadankareem2026.ui.prayer.mapper

import com.hathway.ramadankareem2026.ui.home.model.PrayerTimeModel
import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeUiMapper
import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeUiState
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import org.junit.Assert.assertEquals

class PrayerCountdownMapperTest {

    @Test
    fun `returns Maghrib when current time is before Maghrib`() {

        // Given prayer times

        val prayerState = PrayerTimeUiState(
            fajr = LocalTime.of(5, 30),
            sunrise = LocalTime.of(6, 45),
            dhuhr = LocalTime.of(13, 15),
            asr = LocalTime.of(16, 30),
            maghrib = LocalTime.of(19, 10),
            isha = LocalTime.of(20, 30)
        )

        // Current time = 18:40
        val now = LocalDateTime.of(
            LocalDate.now(), LocalTime.of(18, 40)
        )

        // When
        val result = PrayerCountdownMapper.nextPrayerCountdown(
            prayerState, now
        )

        // Then
        assertEquals("Maghrib", result.prayerName)
        assertEquals(30, result.minutes)
    }

    @Test
    fun `maps prayer times and marks next prayer correctly`() {

        val state = PrayerTimeUiState(
            fajr = LocalTime.of(5, 30),
            sunrise = LocalTime.of(6, 45),
            dhuhr = LocalTime.of(13, 15),
            asr = LocalTime.of(16, 30),
            maghrib = LocalTime.of(19, 10),
            isha = LocalTime.of(20, 30)
        )

        val result: List<PrayerTimeModel> = PrayerTimeUiMapper.map(state)

        // Basic sanity
        assertEquals(5, result.size)
        assertEquals("Fajr", result[0].name)
        assertEquals("Dhuhr", result[1].name)
        assertEquals("Asr", result[2].name)
        assertEquals("Maghrib", result[3].name)
        assertEquals("Isha", result[4].name)

        // Exactly ONE prayer must be marked current
        val currentCount = result.count { it.isCurrent }
        assertEquals(1, currentCount)
    }

    @Test
    fun `highlights Maghrib when time is before Maghrib`() {

        val state = PrayerTimeUiState(
            fajr = LocalTime.of(5, 30),
            sunrise = LocalTime.of(6, 45),
            dhuhr = LocalTime.of(13, 15),
            asr = LocalTime.of(16, 30),
            maghrib = LocalTime.of(19, 10),
            isha = LocalTime.of(20, 30)
        )

        val now = LocalTime.of(18, 40)

        val result = PrayerTimeUiMapper.map(state, now)

        val current = result.first { it.isCurrent }

        assertEquals("Maghrib", current.name)
    }

    @Test
    fun `rolls over to Fajr after Isha`() {

        val state = PrayerTimeUiState(
            fajr = LocalTime.of(5, 30),
            sunrise = LocalTime.of(6, 45),
            dhuhr = LocalTime.of(13, 15),
            asr = LocalTime.of(16, 30),
            maghrib = LocalTime.of(19, 10),
            isha = LocalTime.of(20, 30)
        )

        val now = LocalTime.of(21, 0)

        val result = PrayerTimeUiMapper.map(state, now)

        val current = result.first { it.isCurrent }

        assertEquals("Fajr", current.name)
    }


    @Test
    fun `returns correct minutes before Maghrib`() {

        val prayerState = PrayerTimeUiState(
            fajr = LocalTime.of(5, 30),
            sunrise = LocalTime.of(6, 45),
            dhuhr = LocalTime.of(13, 15),
            asr = LocalTime.of(16, 30),
            maghrib = LocalTime.of(19, 10),
            isha = LocalTime.of(20, 30)
        )

        // Current time: 18:40
        val now = LocalDateTime.of(
            LocalDate.now(), LocalTime.of(18, 40)
        )

        val result = PrayerCountdownMapper.nextPrayerCountdown(
            state = prayerState, now = now
        )

        /*
         Maghrib: 19:10
         Now:      18:40
         Difference: 30 minutes ✅

         a*/
        assertEquals("Maghrib", result.prayerName)
        assertEquals(30, result.minutes)
    }

    @Test
    fun `returns correct minutes after Isha rolling to next day Fajr`() {

        val prayerState = PrayerTimeUiState(
            fajr = LocalTime.of(5, 30),
            sunrise = LocalTime.of(6, 45),
            dhuhr = LocalTime.of(13, 15),
            asr = LocalTime.of(16, 30),
            maghrib = LocalTime.of(19, 10),
            isha = LocalTime.of(20, 30)
        )

        // Current time: 23:00
        val now = LocalDateTime.of(
            LocalDate.now(), LocalTime.of(23, 0)
        )

        val result = PrayerCountdownMapper.nextPrayerCountdown(
            state = prayerState, now = now
        )

        /*
        Now: 23:00
        Next Fajr: 05:30 (next day)

        23:00 → 24:00 = 60 min
        00:00 → 05:30 = 330 min
        Total = 390 min ✅

        */

        // From 23:00 → next day 05:30 = 390 minutes
        assertEquals("Fajr", result.prayerName)
        assertEquals(390, result.minutes)
    }
}