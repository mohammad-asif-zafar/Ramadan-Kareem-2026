package com.hathway.ramadankareem2026.ui.mosques.data.repository

import com.hathway.ramadankareem2026.ui.mosques.domain.model.Mosque

class MosqueRepository {

    fun getNearbyMosques(
        userLat: Double, userLng: Double
    ): List<Mosque> {
        return listOf(
            Mosque(
                id = "1",
                name = "Masjid Negara",
                lat = 3.1412,
                lng = 101.6916,
                distanceMeters = 450,
                address = "Kuala Lumpur"
            ), Mosque(
                id = "2",
                name = "Masjid Jamek",
                lat = 3.1486,
                lng = 101.6958,
                distanceMeters = 820,
                address = "KL City"
            )
        ).sortedBy { it.distanceMeters }
    }
}
