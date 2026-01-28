package com.hathway.ramadankareem2026.ui.mosques.data.repository

import android.util.Log
import com.hathway.ramadankareem2026.BuildConfig
import com.hathway.ramadankareem2026.ui.mosques.data.remote.PlacesApi
import com.hathway.ramadankareem2026.ui.mosques.domain.model.Mosque
import com.hathway.ramadankareem2026.ui.mosques.domain.repository.MosqueRepository
import com.hathway.ramadankareem2026.ui.mosques.util.DistanceUtil

class MosqueRepositoryImpl(
    private val api: PlacesApi
) : MosqueRepository {
    private val TAG = "MosqueRepositoryImpl"
    override suspend fun getNearbyMosques(
        userLat: Double, userLng: Double
    ): List<Mosque> {

        return try {
            val response = api.nearbyMosques(
                location = "$userLat,$userLng",
                apiKey = BuildConfig.MAPS_API_KEY
            )

            response.results.map {
                Mosque(
                    id = it.placeId,
                    name = it.name,
                    lat = it.geometry.location.lat,
                    lng = it.geometry.location.lng,
                    address = it.vicinity ?: "",
                    distanceMeters = DistanceUtil.calculate(
                        userLat,
                        userLng,
                        it.geometry.location.lat,
                        it.geometry.location.lng
                    )
                )
            }.sortedBy { it.distanceMeters }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to load nearby mosques", e)
            emptyList()
        }
    }
}
