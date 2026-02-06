package com.hathway.ramadankareem2026.ui.home.model

import androidx.compose.runtime.Immutable
import com.hathway.ramadankareem2026.ui.prayer.PrayerType
import java.time.LocalTime

@Immutable
data class PrayerDomain(
    val type: PrayerType,          // ✅ enum, not string
    val time: LocalTime,
    val isCurrent: Boolean = false,
    val isNext: Boolean = false,
    val remainingMinutes: Int? = 0,
    val isPast: Boolean = false
)
