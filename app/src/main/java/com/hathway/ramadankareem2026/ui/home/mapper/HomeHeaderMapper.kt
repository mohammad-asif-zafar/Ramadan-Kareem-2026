package com.hathway.ramadankareem2026.ui.home.mapper

import com.hathway.ramadankareem2026.core.util.gregorianFormatter
import com.hathway.ramadankareem2026.core.util.timeFormatter
import com.hathway.ramadankareem2026.ui.home.model.HeaderPage
import com.hathway.ramadankareem2026.ui.home.model.HeaderType
import com.hathway.ramadankareem2026.ui.home.model.PrayerDomain
import java.time.LocalDate


fun buildDynamicPrayerHeader(
    prayer: PrayerDomain?,
    gregorianDate: LocalDate,
    hijriDate: String,
    title: String,
    calculatingText: String,
    prayerTypeText: String,
    remainingText: String
): HeaderPage {

    val dateLine =
        "${gregorianDate.format(gregorianFormatter)} • $hijriDate"

    if (prayer == null || prayer.remainingMinutes == null) {
        return HeaderPage(
            type = HeaderType.DYNAMIC_PRAYER,
            title = title,
            subtitle = dateLine,
            hint = calculatingText
        )
    }

    val prayerTime = prayer.time.format(timeFormatter)

    return HeaderPage(
        type = HeaderType.DYNAMIC_PRAYER,
        title = title,
        subtitle = dateLine,
        hint = listOf(
            prayerTypeText,
            prayerTime,
            remainingText
        ).joinToString(" • ")
    )
}

