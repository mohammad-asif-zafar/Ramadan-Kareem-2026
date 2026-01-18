package com.hathway.ramadankareem2026.ui.widget

import android.content.Context
import com.hathway.ramadankareem2026.core.location.LocationDataStore
import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeCalculator
import com.hathway.ramadankareem2026.ui.prayer.mapper.PrayerCountdownMapper

/**
 * PrayerWidgetDataProvider
 *
 * PURPOSE:
 * - Provides lightweight prayer + countdown data for widgets
 *
 * IMPORTANT:
 * - Widgets cannot use ViewModels
 * - Widgets must work offline
 * - This uses blocking calls intentionally
 */
object PrayerWidgetDataProvider {

    /**
     * Loads widget data synchronously.
     * Safe to call from AppWidgetProvider / WorkManager.
     */
    fun load(context: Context): PrayerWidgetState {

        // 📍 Location store (always returns DEMO or REAL)
        val locationStore = LocationDataStore(context.applicationContext)

        // ✅ Blocking call is allowed for widgets
        val location = locationStore.getBlocking()

        // 🧮 Calculate prayer times using current location
        val calculator = PrayerTimeCalculator()
        val prayerState = calculator.calculate(
            location.latitude, location.longitude
        )

        // ⏳ Calculate countdown to next prayer
        val countdown = PrayerCountdownMapper.nextPrayerCountdown(prayerState)

        // 🧩 Build widget state
        return PrayerWidgetState(
            prayerName = countdown.prayerName,
            minutes = countdown.minutesRemaining,
            city = location.city
        )
    }
}

