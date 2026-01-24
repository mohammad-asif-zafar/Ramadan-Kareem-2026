package com.hathway.ramadankareem2026.ui.mosques.util

object DistanceUtil {
    fun calculate(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Int {
        return haversine(lat1, lng1, lat2, lng2)
    }
}
