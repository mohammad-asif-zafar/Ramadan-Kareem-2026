package com.hathway.ramadankareem2026.ui.qibla

data class QiblaUiState(
    val qiblaBearing: Float = 0f,
    val deviceAzimuth: Float = 0f,
    val sensorAccuracy: Int = 0,
    val isSensorAvailable: Boolean = true,
    val isAligned: Boolean = false
)
