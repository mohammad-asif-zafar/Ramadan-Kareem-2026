package com.hathway.ramadankareem2026.ui.qibla.logic

import org.junit.Assert.*
import org.junit.Test

class QiblaVibrationSnapshotTest {

    @Test
    fun `vibrates when aligned first time`() {
        assertTrue(
            shouldVibrate(
                isAligned = true, hasVibrated = false, vibrationEnabled = true
            )
        )
    }

    @Test
    fun `does not vibrate again if already vibrated`() {
        assertFalse(
            shouldVibrate(
                isAligned = true, hasVibrated = true, vibrationEnabled = true
            )
        )
    }

    @Test
    fun `does not vibrate when disabled`() {
        assertFalse(
            shouldVibrate(
                isAligned = true, hasVibrated = false, vibrationEnabled = false
            )
        )
    }
}