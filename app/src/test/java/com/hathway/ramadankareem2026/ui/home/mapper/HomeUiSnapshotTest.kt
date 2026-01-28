package com.hathway.ramadankareem2026.ui.home.mapper

import com.hathway.ramadankareem2026.ui.home.model.HomeUiState
import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeUiState
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class HomeUiSnapshotTest {

    @Test
    fun `home ui snapshot before Maghrib`() {

        val prayerState = PrayerTimeUiState(
            fajr = LocalTime.of(5, 30),
            sunrise = LocalTime.of(6, 45),
            dhuhr = LocalTime.of(13, 15),
            asr = LocalTime.of(16, 30),
            maghrib = LocalTime.of(19, 10),
            isha = LocalTime.of(20, 30),
            gregorianDate = TODO(),
            hijriDate = TODO()
        )

        val now = LocalDateTime.of(
            LocalDate.now(), LocalTime.of(18, 40)
        )

        val snapshot: HomeUiState = HomeUiStateMapper.map(
            city = "Kuala Lumpur", country = "Malaysia", prayerState = prayerState, now = now
        )

        // 🔒 SNAPSHOT ASSERTIONS
        assertEquals(
            "Kuala Lumpur, Malaysia", snapshot.locationLabel
        )

        assertEquals(
            "Maghrib in 30 minutes", snapshot.headerSubtitle
        )

        // Exactly one NEXT prayer
        assertEquals(
            1, snapshot.prayerTimes.count { it.isCurrent })

        // NEXT prayer is Maghrib
        assertEquals(
            "Maghrib", snapshot.prayerTimes.first { it.isCurrent }.name
        )
    }
}
