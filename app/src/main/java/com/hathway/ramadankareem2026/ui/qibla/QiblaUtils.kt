package com.hathway.ramadankareem2026.ui.qibla

import android.hardware.SensorManager
import kotlin.math.*
import kotlin.math.abs
import kotlin.math.min
private const val KAABA_LAT = 21.4225
private const val KAABA_LNG = 39.8262

fun calculateQiblaBearing(
    userLat: Double,
    userLng: Double
): Float {

    val lat1 = Math.toRadians(userLat)
    val lon1 = Math.toRadians(userLng)
    val lat2 = Math.toRadians(KAABA_LAT)
    val lon2 = Math.toRadians(KAABA_LNG)

    val dLon = lon2 - lon1

    val y = sin(dLon)
    val x = cos(lat1) * tan(lat2) - sin(lat1) * cos(dLon)

    val bearing = atan2(y, x)
    return ((Math.toDegrees(bearing) + 360) % 360).toFloat()
}

fun accuracyLabel(accuracy: Int): String = when (accuracy) {
    SensorManager.SENSOR_STATUS_ACCURACY_HIGH -> "High accuracy"
    SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM -> "Medium accuracy"
    SensorManager.SENSOR_STATUS_ACCURACY_LOW -> "Low accuracy"
    else -> "Unreliable"
}

fun accuracyColor(accuracy: Int) = when (accuracy) {
    SensorManager.SENSOR_STATUS_ACCURACY_HIGH -> androidx.compose.ui.graphics.Color(0xFF2E7D32) // Green
    SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM -> androidx.compose.ui.graphics.Color(0xFFF9A825) // Amber
    else -> androidx.compose.ui.graphics.Color(0xFFC62828) // Red
}

fun isQiblaAligned(
    qiblaBearing: Float,
    deviceAzimuth: Float,
    threshold: Float = 3f
): Boolean {
    val diff = abs(qiblaBearing - deviceAzimuth)
    val angleDiff = min(diff, 360 - diff)
    return angleDiff <= threshold
}