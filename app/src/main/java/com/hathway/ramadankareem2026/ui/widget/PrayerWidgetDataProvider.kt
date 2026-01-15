package com.hathway.ramadankareem2026.ui.widget

import android.content.Context
import com.hathway.ramadankareem2026.ui.home.data.LocationDataStore
import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeCalculator
import com.hathway.ramadankareem2026.ui.prayer.mapper.PrayerCountdownMapper

object PrayerWidgetDataProvider {

     fun load(context: Context): PrayerWidgetState {
        val locationStore = LocationDataStore(context)
        val location = locationStore.getLocationBlocking()

        if (location == null || location.latitude == null || location.longitude == null) {
            return PrayerWidgetState(
                prayerName = "Prayer", minutes = 0, city = "Location not set"
            )
        }

        val calculator = PrayerTimeCalculator()
        val prayerState = calculator.calculate(
            location.latitude, location.longitude
        )

        val countdown = PrayerCountdownMapper.nextPrayerCountdown(prayerState)

        return PrayerWidgetState(
            prayerName = countdown.prayerName,
            minutes = countdown.minutes,
            city = location.city ?: ""
        )
    }
}
