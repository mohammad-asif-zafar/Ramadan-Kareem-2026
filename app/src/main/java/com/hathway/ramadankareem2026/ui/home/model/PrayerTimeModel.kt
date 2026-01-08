package com.hathway.ramadankareem2026.ui.home.model

import androidx.compose.ui.graphics.vector.ImageVector

data class PrayerTimeModel(
    val name: String,
    val time: String,
    val icon: ImageVector,
    val isCurrent: Boolean = false
)
