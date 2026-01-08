package com.hathway.ramadankareem2026.ui.qibla

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.ramadankareem2026.ui.home.data.LocationDataStore
import com.hathway.ramadankareem2026.ui.qibla.sensor.CompassSensor
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class QiblaViewModel(
    app: Application
) : AndroidViewModel(app) {

    private val dataStore = LocationDataStore(app)
    private val compass = CompassSensor(app)

    private val _uiState = MutableStateFlow(QiblaUiState())
    val uiState: StateFlow<QiblaUiState> = _uiState

    init {
        restoreLocation()
    }

    private fun restoreLocation() {
        viewModelScope.launch {
            val saved = dataStore.getLocation()
            if (saved?.latitude != null && saved.longitude != null) {
                val bearing = calculateQiblaBearing(
                    saved.latitude,
                    saved.longitude
                )
                _uiState.value = _uiState.value.copy(
                    qiblaBearing = bearing
                )
            }
        }
    }

    fun startCompass() {
        if (!compass.isAvailable) {
            _uiState.value = _uiState.value.copy(isSensorAvailable = false)
            return
        }

        compass.start()

        viewModelScope.launch {
            compass.azimuth.collect { azimuth ->
                _uiState.value = _uiState.value.copy(
                    deviceAzimuth = azimuth
                )
            }
        }

        viewModelScope.launch {
            compass.accuracy.collect { accuracy ->
                _uiState.value = _uiState.value.copy(
                    sensorAccuracy = accuracy
                )
            }
        }
    }

    fun stopCompass() {
        compass.stop()
    }

    fun setUserLocation(lat: Double, lng: Double) {
        val bearing = calculateQiblaBearing(lat, lng)
        _uiState.value = _uiState.value.copy(
            qiblaBearing = bearing
        )
    }
}
