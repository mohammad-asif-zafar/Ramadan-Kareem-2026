package com.hathway.ramadankareem2026.ui.qibla.mapper

import com.hathway.ramadankareem2026.ui.qibla.QiblaUiState
import com.hathway.ramadankareem2026.ui.qibla.data.QiblaPreferences
import kotlin.math.abs
import kotlin.math.min

object QiblaUiStateMapper {

    fun map(
        qiblaBearing: Float,
        deviceAzimuth: Float,
        sensorAccuracy: Int,
        preferences: QiblaPreferences,
        threshold: Float = 3f
    ): QiblaUiState {

        val diff = abs(qiblaBearing - deviceAzimuth)
        val angleDiff = min(diff, 360 - diff)

        val isAligned = angleDiff <= threshold

        return QiblaUiState(
            qiblaBearing = qiblaBearing,
            deviceAzimuth = deviceAzimuth,
            sensorAccuracy = sensorAccuracy,
            isAligned = isAligned,
            preferences = preferences
        )
    }
}
