package com.hathway.ramadankareem2026.ui.qibla.logic

import kotlin.math.*

object QiblaBearingCalculator {

    fun calculate(
        lat: Double, lng: Double
    ): Float {
        val kaabaLat = Math.toRadians(21.4225)
        val kaabaLng = Math.toRadians(39.8262)

        val userLat = Math.toRadians(lat)
        val userLng = Math.toRadians(lng)

        val deltaLng = kaabaLng - userLng

        val y = sin(deltaLng)
        val x = cos(userLat) * tan(kaabaLat) - sin(userLat) * cos(deltaLng)

        val bearing = (Math.toDegrees(atan2(y, x)) + 360) % 360

        return bearing.toFloat()
    }
}