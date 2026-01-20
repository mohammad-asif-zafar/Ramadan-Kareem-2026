package com.hathway.ramadankareem2026.ui.qibla.components

 fun snappedQiblaRotation(
    deviceAzimuth: Float,
    qiblaBearing: Float,
    snapThreshold: Float = 1f
): Pair<Float, Boolean> {

    val rawRotation = qiblaBearing - deviceAzimuth

    val normalized = ((rawRotation + 540) % 360) - 180
    val isAligned = kotlin.math.abs(normalized) <= snapThreshold

    return if (isAligned) {
        0f to true   // snap needle to center
    } else {
        normalized to false
    }
}
