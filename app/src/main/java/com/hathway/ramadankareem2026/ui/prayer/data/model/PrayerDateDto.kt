package com.hathway.ramadankareem2026.ui.prayer.data.model


/**
 * Date container (Hijri + Gregorian)
 */
data class PrayerDateDto(
    val readable: String,
    val hijri: HijriDto,
    val gregorian: GregorianDto
)
