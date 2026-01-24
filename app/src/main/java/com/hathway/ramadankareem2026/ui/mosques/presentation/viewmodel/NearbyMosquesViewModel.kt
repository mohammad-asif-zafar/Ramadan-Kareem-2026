package com.hathway.ramadankareem2026.ui.mosques.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.ramadankareem2026.core.location.LocationSource
import com.hathway.ramadankareem2026.ui.mosques.domain.model.Mosque
import com.hathway.ramadankareem2026.ui.mosques.domain.usecase.GetNearbyMosquesUseCase
import com.hathway.ramadankareem2026.ui.mosques.domain.usecase.GetUserLocationForMosquesUseCase
import com.hathway.ramadankareem2026.ui.mosques.presentation.state.MosqueUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NearbyMosquesViewModel(
    private val getLocation: GetUserLocationForMosquesUseCase,
    private val getNearbyMosques: GetNearbyMosquesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MosqueUiState())
    val uiState: StateFlow<MosqueUiState> = _uiState

    fun load() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            // 1️⃣ Always get DEMO or cached first
            val location = getLocation()

            // 2️⃣ Fetch mosques using current location
            val mosques = getNearbyMosques(
                location.latitude, location.longitude
            )

            _uiState.value = MosqueUiState(
                userLocation = location, mosques = mosques
            )

            // 3️⃣ LocationRepository may auto-upgrade to REAL later
            refreshIfLocationUpgraded()
        }
    }

    private suspend fun refreshIfLocationUpgraded() {
        val upgraded = getLocation()

        if (upgraded.source == LocationSource.GPS) {
            val mosques = getNearbyMosques(
                upgraded.latitude, upgraded.longitude
            )

            _uiState.value = _uiState.value.copy(
                userLocation = upgraded, mosques = mosques
            )
        }
    }

    fun selectMosque(mosque: Mosque) {
        _uiState.value = _uiState.value.copy(selectedMosque = mosque)
    }
}

