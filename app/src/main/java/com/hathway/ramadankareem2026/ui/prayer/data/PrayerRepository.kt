package com.hathway.ramadankareem2026.ui.prayer.data


import android.content.Context
import android.util.Log
import com.batoulapps.adhan.*
import com.batoulapps.adhan.data.DateComponents
import com.hathway.ramadankareem2026.data.datastore.PrayerCacheStore
import com.hathway.ramadankareem2026.ui.home.model.PrayerDomain
import com.hathway.ramadankareem2026.ui.prayer.mapper.PrayerAdhanMapper
import java.time.LocalDate
import java.time.LocalDateTime

class PrayerRepository(context: Context) {

    private val cache = PrayerCacheStore(context)
    private val TAG = "PrayerRepository"
    suspend fun load(
        latitude: Double?, longitude: Double?
    ): List<PrayerDomain> {
        Log.e(TAG, "load:latitude " + latitude)
        Log.e(TAG, "load:longitude " + longitude)
        // 1️⃣ Try cache
        cache.load()?.let { return it }

        // 2️⃣ Try Adhan API
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

        // 3️⃣ Fallback demo
        return PrayerDemoData.get()
    }/*    suspend fun load(
            latitude: Double?,
            longitude: Double?
        ): List<PrayerDomain> {

            // 1️⃣ Try cache
            cache.load()?.let { return it }

            // 2️⃣ Try Adhan API
            if (latitude != null && longitude != null) {
                val params = CalculationMethod.MUSLIM_WORLD_LEAGUE.parameters
                val today = LocalDate.now()


                val prayerTimes = PrayerTimes(
                    Coordinates(latitude, longitude),
                    DateComponents.from(LocalDate.now()),
                    params
                )

                val mapped =
                    PrayerAdhanMapper.map(prayerTimes, LocalDateTime.now())

                cache.save(mapped)
                return mapped
            }

            // 3️⃣ Fallback demo
            return PrayerDemoData.get()
        }*/
}
