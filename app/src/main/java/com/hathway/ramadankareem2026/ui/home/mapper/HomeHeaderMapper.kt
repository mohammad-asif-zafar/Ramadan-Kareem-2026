package com.hathway.ramadankareem2026.ui.home.mapper

import com.hathway.ramadankareem2026.ui.home.model.HeaderPage
import com.hathway.ramadankareem2026.ui.home.model.HeaderType
import com.hathway.ramadankareem2026.ui.home.model.PrayerDomain
import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeUiMapper.formatRemaining

fun buildDynamicPrayerHeader(
    prayer: PrayerDomain?
): HeaderPage {

    if (prayer == null || prayer.remainingMinutes == null) {
        return HeaderPage(
            type = HeaderType.DYNAMIC_PRAYER,
            title = "Ramadan Kareem 🌙",
            subtitle = "Calculating prayer time…",
            hint = "Prepare your heart for prayer"
        )
    }

    val remaining = formatRemaining(
        prayer.remainingMinutes,
        isCurrent = prayer.isCurrent
    )

    return HeaderPage(
        type = HeaderType.DYNAMIC_PRAYER,
        title = "Ramadan Kareem 🌙",
        subtitle = "${prayer.name} in $remaining",
        hint = "Prepare for prayer & reflection"
    )
}

