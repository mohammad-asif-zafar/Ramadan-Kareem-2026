package com.hathway.ramadankareem2026.ui.qibla

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.ramadankareem2026.ui.home.data.LocationDataStore
import com.hathway.ramadankareem2026.ui.qibla.data.QiblaPreferencesDataStore
import com.hathway.ramadankareem2026.ui.qibla.sensor.CompassSensor
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class QiblaViewModel(
    app: Application
) : AndroidViewModel(app) {
    private var hasVibrated = false

    private val preferencesStore = QiblaPreferencesDataStore(app)

    private val dataStore = LocationDataStore(app)
    private val compass = CompassSensor(app)

    private val _uiState = MutableStateFlow(QiblaUiState())
    val uiState: StateFlow<QiblaUiState> = _uiState

    init {
        restoreLocation()
        restorePreferences()
    }

    private fun restorePreferences() {
        viewModelScope.launch {
            val prefs = preferencesStore.getPreferences()
            _uiState.value = _uiState.value.copy(
                preferences = prefs
            )
        }
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

                val aligned = isQiblaAligned(
                    qiblaBearing = _uiState.value.qiblaBearing,
                    deviceAzimuth = azimuth
                )

                if (!aligned) {
                    hasVibrated = false
                }

                if (aligned &&
                    !hasVibrated &&
                    _uiState.value.preferences.vibrationEnabled) {

                    vibrateOnce(getApplication())
                    hasVibrated = true
                }

                _uiState.value = _uiState.value.copy(
                    deviceAzimuth = azimuth,
                    isAligned = aligned
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
    @SuppressLint("MissingPermission")
    private fun vibrateOnce(context: Context) {
        val vibrator =
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        if (vibrator.hasVibrator()) {
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    40,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        }
    }
    fun updatePreferences(
        vibrationEnabled: Boolean? = null,
        showAlignmentText: Boolean? = null,
        showCalibrationHint: Boolean? = null
    ) {
        val current = _uiState.value.preferences

        val updated = current.copy(
            vibrationEnabled = vibrationEnabled ?: current.vibrationEnabled,
            showAlignmentText = showAlignmentText ?: current.showAlignmentText,
            showCalibrationHint = showCalibrationHint ?: current.showCalibrationHint
        )

        _uiState.value = _uiState.value.copy(preferences = updated)

        viewModelScope.launch {
            preferencesStore.savePreferences(updated)
        }
    }
}
