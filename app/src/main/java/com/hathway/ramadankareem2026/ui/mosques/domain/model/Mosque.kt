package com.hathway.ramadankareem2026.ui.mosques.domain.model

data class Mosque(
    val id: String,
    val name: String,
    val lat: Double,
    val lng: Double,
    val address: String,
    val distanceMeters: Int
)

