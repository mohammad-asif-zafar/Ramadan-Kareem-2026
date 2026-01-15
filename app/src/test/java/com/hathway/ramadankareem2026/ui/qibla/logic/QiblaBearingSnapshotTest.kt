package com.hathway.ramadankareem2026.ui.qibla.logic

import org.junit.Assert.assertEquals
import org.junit.Test

class QiblaBearingSnapshotTest {

    @Test
    fun `qibla bearing from Kuala Lumpur`() {
        val bearing = QiblaBearingCalculator.calculate(
            lat = 3.1390, lng = 101.6869
        )

        // Expected ≈ 292°
        assertEquals(292f, bearing, 2f)
    }

    @Test
    fun `qibla bearing wrap-around safe`() {
        val bearing = QiblaBearingCalculator.calculate(
            lat = 0.0, lng = 179.9
        )

        assert(bearing in 0f..360f)
    }
}