package com.hathway.ramadankareem2026.ui.home.mapper

import com.hathway.ramadankareem2026.core.util.gregorianFormatter
import com.hathway.ramadankareem2026.core.util.timeFormatter
import com.hathway.ramadankareem2026.ui.home.model.HeaderPage
import com.hathway.ramadankareem2026.ui.home.model.HeaderType
import com.hathway.ramadankareem2026.ui.home.model.PrayerDomain
import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeUiMapper.formatRemaining
import java.time.LocalDate


fun buildDynamicPrayerHeader(
    prayer: PrayerDomain?, gregorianDate: LocalDate, hijriDate: String
): HeaderPage {

    // Date line (small text)
    val dateLine = "${gregorianDate.format(gregorianFormatter)} • $hijriDate"

    // Fallback (loading / demo)
    if (prayer == null || prayer.remainingMinutes == null) {
        return HeaderPage(
            type = HeaderType.DYNAMIC_PRAYER,
            title = "Ramadan Kareem 🌙",
            subtitle = dateLine,
            hint = "Calculating prayer time…"
        )
    }

    // Next prayer time
    val prayerTime = prayer.time.format(timeFormatter)

    return HeaderPage(
        type = HeaderType.DYNAMIC_PRAYER,
        title = "Ramadan Kareem 🌙",
        subtitle = dateLine,
        hint = "${prayer.name} • $prayerTime • ${formatRemaining(prayer.remainingMinutes, false)}"

    )
}
