package com.hathway.ramadankareem2026.ui.ramadan.logic

import com.hathway.ramadankareem2026.ui.ramadan.model.FastingDayStatus
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class RamadanMonthlyOverviewSnapshotTest {

    @Test
    fun `monthly fasting overview snapshot`() {

        val ramadanStart = LocalDate.of(2026, 2, 18)
        val now = LocalDateTime.of(2026, 2, 20, 12, 0)

        val result = RamadanMonthlyOverviewCalculator.calculate(
            ramadanStartDate = ramadanStart,
            totalDays = 30,
            fajrTime = LocalTime.of(5, 30),
            maghribTime = LocalTime.of(19, 10),
            now = now
        )

        // 🔒 SNAPSHOT ASSERTIONS

        assertEquals(30, result.size)

        // Day 1 & 2 completed
        assertEquals(FastingDayStatus.COMPLETED, result[0].status)
        assertEquals(FastingDayStatus.COMPLETED, result[1].status)

        // Day 3 is current fasting day
        assertEquals(FastingDayStatus.FASTING, result[2].status)

        // Day 4 is upcoming
        assertEquals(FastingDayStatus.UPCOMING, result[3].status)

        // Day numbers correct
        assertEquals(1, result[0].dayNumber)
        assertEquals(30, result.last().dayNumber)
    }
}