package com.hathway.ramadankareem2026.ui.qibla.mapper

import com.hathway.ramadankareem2026.ui.qibla.data.QiblaPreferences
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class QiblaPreferencesSnapshotTest {

    @Test
    fun `default preferences snapshot`() {
        val prefs = QiblaPreferences()

        assertTrue(prefs.vibrationEnabled)
        assertTrue(prefs.showAlignmentText)
        assertTrue(prefs.showCalibrationHint)
    }

    @Test
    fun `custom preferences snapshot`() {
        val prefs = QiblaPreferences(
            vibrationEnabled = false, showAlignmentText = false, showCalibrationHint = true
        )

        assertFalse(prefs.vibrationEnabled)
        assertFalse(prefs.showAlignmentText)
        assertTrue(prefs.showCalibrationHint)
    }
}
