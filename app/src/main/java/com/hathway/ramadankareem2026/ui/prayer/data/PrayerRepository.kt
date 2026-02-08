package com.hathway.ramadankareem2026.ui.prayer.data


import android.content.Context
import com.batoulapps.adhan.*
import com.batoulapps.adhan.data.DateComponents
import com.hathway.ramadankareem2026.core.location.LocationUiState
import com.hathway.ramadankareem2026.core.util.TimezoneDetector
import com.hathway.ramadankareem2026.data.datastore.PrayerCacheStore
import com.hathway.ramadankareem2026.ui.home.model.PrayerDomain
import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeUiState
import com.hathway.ramadankareem2026.ui.prayer.data.api.PrayerApiService
import com.hathway.ramadankareem2026.ui.prayer.mapper.PrayerAdhanMapper
import com.hathway.ramadankareem2026.ui.prayer.mapper.PrayerApiMapper
import java.time.LocalDate
import java.time.LocalDateTime

class PrayerRepository(private val context: Context, private val api: PrayerApiService) {

    private val cache = PrayerCacheStore(context)
    suspend fun load(
        latitude: Double?, longitude: Double?
    ): List<PrayerDomain> {

        // ️ Try cache
        cache.load()?.let { return it }

        // Try Adhan API
        if (latitude != null && longitude != null) {

            val params = CalculationMethod.MUSLIM_WORLD_LEAGUE.parameters

            val today = LocalDate.now()
            val dateComponents = DateComponents(
                today.year, today.monthValue, today.dayOfMonth
            )

            val prayerTimes = PrayerTimes(
                Coordinates(latitude, longitude), dateComponents, params
            )

            val mapped = PrayerAdhanMapper.map(prayerTimes, LocalDateTime.now())

            cache.save(mapped)
            return mapped
        }

        // Fallback demo
        return PrayerDemoData.get()
    }

    suspend fun loadFromApi(
        date: String, lat: Double, lng: Double
    ): PrayerTimeUiState {
        return try {
            // Detect timezone automatically based on location

            val timezone = TimezoneDetector.detectTimezone(context, lat, lng)
            android.util.Log.d("PrayerRepository", "Detected timezone: $timezone for coordinates: $lat, $lng")
            
            // Only pass timezone if it's not null and not empty
            if (timezone.isNotBlank()) {
                PrayerApiMapper.map(
                    api.getTimings(date, lat, lng, timezone = timezone)
                )
            } else {
                PrayerApiMapper.map(
                    api.getTimings(date, lat, lng)
                )
            }
        } catch (e: Exception) {
            android.util.Log.e("PrayerRepository", "Failed to load prayer times from API", e)
            PrayerDemoData.demo()
        }
    }
}
