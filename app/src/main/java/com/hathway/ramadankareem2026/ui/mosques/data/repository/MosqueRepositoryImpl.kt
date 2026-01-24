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
    private  val TAG = "MosqueRepositoryImpl"
    override suspend fun getNearbyMosques(
        userLat: Double,
        userLng: Double
    ): List<Mosque> {
        Log.d(TAG, "IND-A: "+BuildConfig.MAPS_API_KEY)

        val response = api.nearbyMosques(
            location = "$userLat,$userLng",
            apiKey = BuildConfig.MAPS_API_KEY
        )
        Log.d(TAG, "getNearbyMosques: "+BuildConfig.MAPS_API_KEY)
        Log.d("MOSQUE_API", "Results count: ${response.results.size}")

        return response.results.map {
            Mosque(
                id = it.placeId,
                name = it.name,
                lat = it.geometry.location.lat,
                lng = it.geometry.location.lng,
                address = it.vicinity ?: "",
                distanceMeters = DistanceUtil.calculate(
                    userLat, userLng,
                    it.geometry.location.lat,
                    it.geometry.location.lng
                )
            )
        }.sortedBy { it.distanceMeters }
    }
}
