package com.hathway.ramadankareem2026.ui.mosques.domain.repository

import com.hathway.ramadankareem2026.ui.mosques.domain.model.Mosque

interface MosqueRepository {
    suspend fun getNearbyMosques(
        userLat: Double,
        userLng: Double
    ): List<Mosque>
}
