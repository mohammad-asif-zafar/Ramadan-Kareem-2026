package com.hathway.ramadankareem2026.ui.prayer.data.api


/**
 * Gregorian date details
 */
data class GregorianDto(
    val date: String,     // "01-01-2025"
    val day: String,
    val month: GregorianMonthDto,
    val year: String
)

data class GregorianMonthDto(
    val number: Int,
    val en: String
)
