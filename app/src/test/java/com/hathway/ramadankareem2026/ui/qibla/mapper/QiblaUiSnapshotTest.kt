package com.hathway.ramadankareem2026.ui.qibla.mapper

import com.hathway.ramadankareem2026.ui.qibla.data.QiblaPreferences
import org.junit.Assert.*
import org.junit.Test

class QiblaUiSnapshotTest {

    @Test
    fun `aligned qibla snapshot`() {

        val snapshot = QiblaUiStateMapper.map(
            qiblaBearing = 295f,
            deviceAzimuth = 296f, // within 1°
            sensorAccuracy = 3,
            preferences = QiblaPreferences(
                vibrationEnabled = true,
                showAlignmentText = true,
                showCalibrationHint = true
            )
        )

        // 🔒 SNAPSHOT ASSERTIONS
        assertTrue(snapshot.isAligned)
        assertEquals(295f, snapshot.qiblaBearing, 0.01f)
        assertEquals(296f, snapshot.deviceAzimuth, 0.01f)
        assertTrue(snapshot.preferences.vibrationEnabled)
    }

    @Test
    fun `not aligned qibla snapshot`() {

        val snapshot = QiblaUiStateMapper.map(
            qiblaBearing = 295f,
            deviceAzimuth = 310f, // far off
            sensorAccuracy = 2,
            preferences = QiblaPreferences(
                vibrationEnabled = true,
                showAlignmentText = true,
                showCalibrationHint = true
            )
        )

        assertFalse(snapshot.isAligned)
    }

    @Test
    fun `wrap around alignment near 360 degrees`() {

        val snapshot = QiblaUiStateMapper.map(
            qiblaBearing = 359f,
            deviceAzimuth = 1f, // wrap-around case
            sensorAccuracy = 3,
            preferences = QiblaPreferences()
        )

        assertTrue(snapshot.isAligned)
    }
}
