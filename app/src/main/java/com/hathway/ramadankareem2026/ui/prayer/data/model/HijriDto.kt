package com.hathway.ramadankareem2026.ui.prayer.data.model

/**
 * Hijri date details
 */
data class HijriDto(
    val date: String,     // "01-07-1446"
    val day: String,
    val month: HijriMonthDto,
    val year: String
)

data class HijriMonthDto(
    val number: Int,
    val en: String,
    val ar: String
)
