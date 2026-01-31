package com.hathway.ramadankareem2026.ui.home.homeViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.ramadankareem2026.core.location.LocationRepository
import com.hathway.ramadankareem2026.core.location.LocationSelectionMode
import com.hathway.ramadankareem2026.core.location.LocationUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = LocationRepository(app)

    // Sealed UI state starts in Loading
    private val _locationState = MutableStateFlow<LocationUiState>(LocationUiState.Loading)

    val locationState: StateFlow<LocationUiState> = _locationState

    init {
        loadLocation()
    }

    /**
     * Loads location with DEMO → REAL upgrade logic.
     */
    fun loadLocation() {
        viewModelScope.launch {
            _locationState.value = LocationUiState.Loading

            val result = repository.getLocation()

            _locationState.value = result
        }
    }

    /**
     * Called when user manually selects a location.
     * Overrides auto-detected location.
     */
    fun setUserLocation(
        city: String, country: String, latitude: Double, longitude: Double
    ) {
        viewModelScope.launch {
            val userSelected = LocationUiState.Success(
                city = city,
                country = country,
                latitude = latitude,
                longitude = longitude,
                source = com.hathway.ramadankareem2026.core.location.LocationSource.GPS,
                selectionMode = LocationSelectionMode.USER_SELECTED
            )

            repository.saveUserSelectedLocation(userSelected)

            _locationState.value = userSelected
        }
    }
}

