package com.hathway.ramadankareem2026.core.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.Locale

/**
 * Fetches REAL device location using GPS / Network.
 *
 * RULES:
 * - NEVER saves data
 * - NEVER returns DEMO
 * - May return null (Repository decides fallback)
 */
class LocationProvider(
    context: Context
) {

    // Always hold application context to avoid leaks
    private val appContext = context.applicationContext

    private val fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(appContext)

    /**
     * Attempts to fetch the device's last known location.
     *
     * @return LocationUiState if successful
     * @return null if permissions / GPS / network unavailable
     */
    @SuppressLint("MissingPermission")
    suspend fun fetchLocation(): LocationUiState.Success? {

        // 🚫 Permission check (soft fail)
        if (!hasLocationPermission()) return null

        return try {
            // Get last known location
            val location = fusedLocationClient.lastLocation.await() ?: return null

            // 🌍 Reverse geocode (best effort, never crash)
            val (city, country) = resolveAddress(
                latitude = location.latitude,
                longitude = location.longitude
            )

            // ✅ Return SUCCESS UI state
            LocationUiState.Success(
                city = city,
                country = country,
                latitude = location.latitude,
                longitude = location.longitude,
                source = LocationSource.GPS,
                selectionMode = LocationSelectionMode.AUTO_DETECTED
            )
        } catch (e: Exception) {
            null
        }
    }


    /**
     * Checks if location permission is granted.
     */
    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            appContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    appContext,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Resolves city and country using Geocoder.
     * This is best-effort and never throws.
     */
    private suspend fun resolveAddress(
        latitude: Double,
        longitude: Double
    ): Pair<String, String> = withContext(Dispatchers.IO) {
        try {
            val geocoder = Geocoder(appContext, Locale.getDefault())
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)

            val address = addresses?.firstOrNull()
            val city = address?.locality ?: address?.subAdminArea ?: "Unknown"
            val country = address?.countryName ?: "Unknown"

            city to country
        } catch (e: Exception) {
            "Unknown" to "Unknown"
        }
    }
}
