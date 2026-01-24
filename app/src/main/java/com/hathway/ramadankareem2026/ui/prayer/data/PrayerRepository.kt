package com.hathway.ramadankareem2026.ui.prayer.data


import android.content.Context
import com.batoulapps.adhan.*
import com.batoulapps.adhan.data.DateComponents
import com.hathway.ramadankareem2026.data.datastore.PrayerCacheStore
import com.hathway.ramadankareem2026.ui.home.model.PrayerDomain
import com.hathway.ramadankareem2026.ui.prayer.data.api.PrayerApiService
import com.hathway.ramadankareem2026.ui.prayer.mapper.PrayerAdhanMapper
import com.hathway.ramadankareem2026.ui.prayer.mapper.PrayerApiMapper
import java.time.LocalDate
import java.time.LocalDateTime

class PrayerRepository(context: Context, private val api: PrayerApiService) {

    private val cache = PrayerCacheStore(context)
    private val TAG = "PrayerRepository"
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
    ) = PrayerApiMapper.map(
        api.getTimings(date, lat, lng)
    )
}
