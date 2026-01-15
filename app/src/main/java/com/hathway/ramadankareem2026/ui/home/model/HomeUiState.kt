package com.hathway.ramadankareem2026.ui.home.model

data class HomeUiState(
    val locationLabel: String,
    val headerSubtitle: String,
    val prayerTimes: List<PrayerTimeModel>
)
