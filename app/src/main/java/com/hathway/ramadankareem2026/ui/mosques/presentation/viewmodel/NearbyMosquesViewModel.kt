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
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            runCatching {
                val location = getLocation()
                val mosques = getNearbyMosques(location.latitude, location.longitude)

                _uiState.value = MosqueUiState(
                    userLocation = location,
                    mosques = mosques,
                    isLoading = false,
                    error = null
                )

                refreshIfLocationUpgraded()
            }.onFailure { e ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.localizedMessage ?: "Failed to load nearby mosques"
                )
            }
        }
    }

    private suspend fun refreshIfLocationUpgraded() {
        runCatching {
            val upgraded = getLocation()

            if (upgraded.source == LocationSource.GPS) {
                val mosques = getNearbyMosques(upgraded.latitude, upgraded.longitude)

                _uiState.value = _uiState.value.copy(
                    userLocation = upgraded,
                    mosques = mosques,
                    error = null
                )
            }
        }
    }

    fun selectMosque(mosque: Mosque) {
        _uiState.value = _uiState.value.copy(selectedMosque = mosque)
    }
}

