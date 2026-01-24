package com.hathway.ramadankareem2026.ui.mosques.domain.usecase

import com.hathway.ramadankareem2026.core.location.LocationRepository
import com.hathway.ramadankareem2026.core.location.LocationUiState

// ui/mosques/domain/usecase/GetUserLocationForMosquesUseCase.kt
class GetUserLocationForMosquesUseCase(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(): LocationUiState.Success {
        // Guaranteed Success (DEMO or REAL)
        return locationRepository.getLocation() as LocationUiState.Success
    }
}
