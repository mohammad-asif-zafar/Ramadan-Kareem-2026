package com.hathway.ramadankareem2026.core.location

import android.content.Context

/**
 * Decides which location should be used:
 * DEMO by default → REAL if available
 */
class LocationRepository(
    context: Context
) {

    private val dataStore = LocationDataStore(context.applicationContext)
    private val provider = LocationProvider(context.applicationContext)

    /**
     * Returns a LocationUiState using DEMO → REAL upgrade logic.
     */
    suspend fun getLocation(): LocationUiState {

        // 1️⃣ Always get cached or DEMO (Success only)
        val cached: LocationUiState.Success = dataStore.get()

        // 2️⃣ Try to fetch real location
        val real: LocationUiState.Success? = provider.fetchLocation()

        return if (real != null) {
            // ✅ Save REAL location
            dataStore.save(real)
            real
        } else {
            // ✅ Keep cached / DEMO
            cached
        }
    }

    /**
     * Saves a user-selected location.
     * This must NOT be overridden by auto-detection later.
     */
    suspend fun saveUserSelectedLocation(
        location: LocationUiState.Success
    ) {
        dataStore.save(location)
    }
}
