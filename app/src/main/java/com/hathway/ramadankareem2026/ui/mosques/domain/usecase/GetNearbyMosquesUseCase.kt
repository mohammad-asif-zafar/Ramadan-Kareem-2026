package com.hathway.ramadankareem2026.ui.mosques.domain.usecase

import com.hathway.ramadankareem2026.ui.mosques.data.repository.MosqueRepository
import com.hathway.ramadankareem2026.ui.mosques.domain.model.Mosque

class GetNearbyMosquesUseCase(
    private val repository: MosqueRepository = MosqueRepository()
) {
    operator fun invoke(lat: Double, lng: Double): List<Mosque> {
        return repository.getNearbyMosques(lat, lng)
    }

}
