package com.hathway.ramadankareem2026.ui.home.homeViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.ramadankareem2026.ui.home.data.LocationDataStore
import com.hathway.ramadankareem2026.ui.home.data.LocationSource
import com.hathway.ramadankareem2026.ui.home.data.LocationUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = LocationRepository(app)
    private val dataStore = LocationDataStore(app)

    private val _locationState = MutableStateFlow(LocationUiState())

    val locationState: StateFlow<LocationUiState> = _locationState

    init {
        restoreLocation()
    }

    private fun restoreLocation() {
        viewModelScope.launch {
            val saved = dataStore.getLocation()
            if (saved != null) {
                _locationState.value = saved
            }
        }
    }

    fun setUserLocation(
        city: String, country: String, lat: Double, lng: Double
    ) {
        val state = LocationUiState(
            city = city,
            country = country,
            latitude = lat,
            longitude = lng,
            source = LocationSource.USER_SELECTED
        )
        _locationState.value = state

        viewModelScope.launch {
            dataStore.saveLocation(
                city, country, lat, lng, LocationSource.USER_SELECTED
            )
        }
    }


    fun loadLocation() {
        viewModelScope.launch {
            val result = repo.getCityAndCountryWithLatLng()
            if (result != null) {
                val (city, country, lat, lng) = result
                val state = LocationUiState(
                    city = city,
                    country = country,
                    latitude = lat,
                    longitude = lng,
                    source = LocationSource.AUTO_DETECTED
                )
                _locationState.value = state

                dataStore.saveLocation(
                    city, country, lat, lng, LocationSource.AUTO_DETECTED
                )
            }
        }
    }
}
