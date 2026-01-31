package com.hathway.ramadankareem2026.core.location

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

/**
 * Preferences DataStore instance.
 * Uses application context (safe for long-living storage).
 */
private val Context.dataStore by preferencesDataStore(
    name = "location_prefs"
)

/**
 * Handles local persistence of location data.
 *
 * RULES:
 * 1. Never returns null
 * 2. Always returns a VALID Success state
 * 3. Falls back to DEMO if nothing is saved
 */
class LocationDataStore(
    context: Context
) {

    // Always keep application context
    private val appContext = context.applicationContext

    /* ---------------- Preference Keys ---------------- */

    private val CITY = stringPreferencesKey("city")
    private val COUNTRY = stringPreferencesKey("country")
    private val LAT = doublePreferencesKey("lat")
    private val LNG = doublePreferencesKey("lng")

    private val SOURCE = stringPreferencesKey("source")
    private val SELECTION_MODE = stringPreferencesKey("selection_mode")

    /* ---------------- DEMO FALLBACK ---------------- */

    private companion object {
        const val DEMO_CITY = "Kuala Lumpur"
        const val DEMO_COUNTRY = "Malaysia"
        const val DEMO_LAT = 3.1085715
        const val DEMO_LNG = 101.6769629
    }

    /**
     * Saves ONLY a Success state.
     * Loading/Error must NEVER reach persistence.
     */
    suspend fun save(location: LocationUiState.Success) {
        appContext.dataStore.edit { prefs ->
            prefs[CITY] = location.city
            prefs[COUNTRY] = location.country
            prefs[LAT] = location.latitude
            prefs[LNG] = location.longitude

            prefs[SOURCE] = location.source.name
            prefs[SELECTION_MODE] = location.selectionMode.name
        }
    }

    /**
     * Returns a valid LocationUiState.Success at all times.
     *
     * Priority:
     * 1. Saved location
     * 2. DEMO fallback (auto-saved)
     */
    suspend fun get(): LocationUiState.Success {
        val prefs = appContext.dataStore.data.first()

        val city = prefs[CITY]
        val country = prefs[COUNTRY]
        val lat = prefs[LAT]
        val lng = prefs[LNG]
        val source = prefs[SOURCE]
        val selectionMode = prefs[SELECTION_MODE]

        return if (city != null && country != null && lat != null && lng != null && source != null && selectionMode != null) {
            //  Cached success state
            LocationUiState.Success(
                city = city,
                country = country,
                latitude = lat,
                longitude = lng,
                source = LocationSource.valueOf(source),
                selectionMode = LocationSelectionMode.valueOf(selectionMode)
            )
        } else {
            //  No saved data → create DEMO success
            val demo = LocationUiState.Success(
                city = DEMO_CITY,
                country = DEMO_COUNTRY,
                latitude = DEMO_LAT,
                longitude = DEMO_LNG,
                source = LocationSource.DEMO,
                selectionMode = LocationSelectionMode.AUTO_DETECTED
            )

            // Persist demo immediately
            save(demo)

            demo
        }
    }

    /**
     * Blocking call.
     * Intended ONLY for widgets / WorkManager.
     */
    fun getBlocking(): LocationUiState.Success = runBlocking {
        get()
    }
}
