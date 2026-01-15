package com.hathway.ramadankareem2026.ui.home.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(name = "location_prefs")

class LocationDataStore(private val context: Context) {
    val LAT = doublePreferencesKey("lat")
    val LNG = doublePreferencesKey("lng")

    companion object {
        val CITY = stringPreferencesKey("city")
        val COUNTRY = stringPreferencesKey("country")
        val SOURCE = stringPreferencesKey("source")
    }

    suspend fun saveLocation(
        city: String, country: String, lat: Double, lng: Double, source: LocationSource
    ) {
        context.dataStore.edit { prefs ->
            prefs[CITY] = city
            prefs[COUNTRY] = country
            prefs[LAT] = lat
            prefs[LNG] = lng
            prefs[SOURCE] = source.name
        }
    }

    suspend fun getLocation(): LocationUiState? {
        val prefs = context.dataStore.data.first()

        val city = prefs[CITY]
        val country = prefs[COUNTRY]
        val lat = prefs[LAT]
        val lng = prefs[LNG]
        val source = prefs[SOURCE]

        return if (city != null && country != null && lat != null && lng != null && source != null) {
            LocationUiState(
                city = city,
                country = country,
                latitude = lat,
                longitude = lng,
                source = LocationSource.valueOf(source)
            )
        } else null
    }

    // 👇 FOR WIDGETS ONLY
    fun getLocationBlocking(): LocationUiState? = kotlinx.coroutines.runBlocking {
        getLocation()
    }
}
