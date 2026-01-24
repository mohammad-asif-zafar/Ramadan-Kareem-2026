package com.hathway.ramadankareem2026.ui.mosques.domain.usecase

import com.hathway.ramadankareem2026.ui.mosques.domain.repository.MosqueRepository

class GetNearbyMosquesUseCase(
    private val repository: MosqueRepository
) {
    suspend operator fun invoke(
        lat: Double,
        lng: Double
    ) = repository.getNearbyMosques(lat, lng)
}
