package com.hathway.ramadankareem2026.ui.home.data

data class LocationUiState(
    val city: String? = null,
    val country: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val source: LocationSource = LocationSource.NONE,
    val error: String? = null
)

enum class LocationSource {
    NONE, USER_SELECTED, AUTO_DETECTED
}