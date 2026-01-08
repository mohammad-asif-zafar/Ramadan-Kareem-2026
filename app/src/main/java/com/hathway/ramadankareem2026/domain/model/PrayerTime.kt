package com.hathway.ramadankareem2026.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

data class PrayerTime(
    val name: String,
    val time: String,
    val icon: ImageVector,
    val isNext: Boolean = false
)
