package com.hathway.ramadankareem2026.ui.mosques.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hathway.ramadankareem2026.ui.mosques.domain.usecase.GetNearbyMosquesUseCase
import com.hathway.ramadankareem2026.ui.mosques.domain.usecase.GetUserLocationForMosquesUseCase

// ui/mosques/presentation/viewmodel/NearbyMosquesViewModelFactory.kt
class NearbyMosquesViewModelFactory(
    private val getLocation: GetUserLocationForMosquesUseCase,
    private val getNearbyMosques: GetNearbyMosquesUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NearbyMosquesViewModel::class.java)) {
            return NearbyMosquesViewModel(
                getLocation = getLocation,
                getNearbyMosques = getNearbyMosques
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
