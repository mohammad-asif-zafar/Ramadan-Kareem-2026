package com.hathway.ramadankareem2026.ui.home.mapper

import com.hathway.ramadankareem2026.ui.home.model.HomeUiState
import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeUiState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class HomeUiSnapshotTest {

    @Test
    fun `home ui snapshot before Maghrib`() {

        val testDate = LocalDate.of(2026, 1, 30)
        val prayerState = PrayerTimeUiState(
            fajr = LocalTime.of(5, 30),
            sunrise = LocalTime.of(6, 45),
            dhuhr = LocalTime.of(13, 15),
            asr = LocalTime.of(16, 30),
            maghrib = LocalTime.of(19, 10),
            isha = LocalTime.of(20, 30),
            gregorianDate = testDate,
            hijriDate = "26 Rajab 1447"
        )

        val now = LocalDateTime.of(testDate, LocalTime.of(18, 40))

        val snapshot: HomeUiState = HomeUiStateMapper.map(
            city = "Kuala Lumpur", country = "Malaysia", prayerState = prayerState, now = now
        )

        // 🔒 SNAPSHOT ASSERTIONS
        assertEquals(
            "Kuala Lumpur, Malaysia", snapshot.locationLabel
        )

        // Should have prayer times
        assertTrue(snapshot.prayerTimes.isNotEmpty())
        
        // Should have a header subtitle with countdown
        assertTrue(snapshot.headerSubtitle.isNotEmpty())
        
        // Should have exactly one current prayer
        assertEquals(
            1, snapshot.prayerTimes.count { it.isCurrent }
        )
    }
}
