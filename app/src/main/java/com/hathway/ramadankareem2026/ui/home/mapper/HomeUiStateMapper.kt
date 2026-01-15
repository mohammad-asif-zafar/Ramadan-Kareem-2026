package com.hathway.ramadankareem2026.ui.home.mapper

import com.hathway.ramadankareem2026.ui.home.model.HomeUiState
import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeUiMapper
import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeUiState
import com.hathway.ramadankareem2026.ui.prayer.mapper.PrayerCountdownMapper

object HomeUiStateMapper {

    fun map(
        city: String,
        country: String,
        prayerState: PrayerTimeUiState,
        now: java.time.LocalDateTime = java.time.LocalDateTime.now()
    ): HomeUiState {

        val countdown = PrayerCountdownMapper.nextPrayerCountdown(prayerState, now)

        val prayerList = PrayerTimeUiMapper.map(prayerState, now.toLocalTime())

        return HomeUiState(
            locationLabel = "$city, $country",
            headerSubtitle = "${countdown.prayerName} in ${countdown.minutes} minutes",
            prayerTimes = prayerList
        )
    }
}
