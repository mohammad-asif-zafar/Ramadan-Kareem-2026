package com.hathway.ramadankareem2026.ui.home.mapper

import com.hathway.ramadankareem2026.ui.home.model.HomeUiState
import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeUiMapper
import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeUiState
import com.hathway.ramadankareem2026.ui.prayer.mapper.PrayerCountdownMapper
import java.time.LocalDateTime

object HomeUiStateMapper {

    fun map(
        city: String,
        country: String,
        prayerState: PrayerTimeUiState,
        now: LocalDateTime = LocalDateTime.now()
    ): HomeUiState {

        val countdown = PrayerCountdownMapper.nextPrayerCountdown(
            state = prayerState,   // ✅ correct parameter name
            now = now
        )

        val minutes = countdown.minutesRemaining.coerceAtLeast(0)
        val minuteLabel = if (minutes == 1) "minute" else "minutes"

        val headerSubtitle = "${countdown.prayerName} in $minutes $minuteLabel"

        val prayerList = PrayerTimeUiMapper.map(
            prayerState, now.toLocalTime()
        )

        return HomeUiState(
            locationLabel = "$city, $country",
            headerSubtitle = headerSubtitle,
            prayerTimes = prayerList
        )
    }
}
