package com.hathway.ramadankareem2026.ui.qibla

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.ramadankareem2026.core.location.LocationDataStore
import com.hathway.ramadankareem2026.ui.qibla.data.QiblaPreferencesDataStore
import com.hathway.ramadankareem2026.ui.qibla.sensor.CompassSensor
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class QiblaViewModel(
    app: Application
) : AndroidViewModel(app) {

    /** Prevents repeated vibration when aligned */
    private var hasVibrated = false

    /** User preferences (vibration, hints, etc.) */
    private val preferencesStore = QiblaPreferencesDataStore(app)

    /** Location source (always returns DEMO or REAL) */
    private val locationStore = LocationDataStore(app)

    /** Compass sensor wrapper */
    private val compass = CompassSensor(app)

    /** Main UI state */
    private val _uiState = MutableStateFlow(QiblaUiState())
    val uiState: StateFlow<QiblaUiState> = _uiState

    /** Jobs to safely cancel compass collectors */
    private var azimuthJob: Job? = null
    private var accuracyJob: Job? = null

    init {
        restoreLocation()
        restorePreferences()
    }

    /**
     * Restore saved user preferences from DataStore
     */
    private fun restorePreferences() {
        viewModelScope.launch {
            val prefs = preferencesStore.getPreferences()
            _uiState.value = _uiState.value.copy(preferences = prefs)
        }
    }

    /**
     * Restore last known location (DEMO or REAL)
     * and calculate Qibla bearing
     */
    private fun restoreLocation() {
        viewModelScope.launch {

            // ✅ get() ALWAYS returns a valid location
            val saved = locationStore.get()

            val bearing = calculateQiblaBearing(
                saved.latitude,
                saved.longitude
            )

            _uiState.value = _uiState.value.copy(
                qiblaBearing = bearing
            )
        }
    }

    /**
     * Starts compass sensor and listens for updates
     */
    fun startCompass() {

        if (!compass.isAvailable) {
            _uiState.value = _uiState.value.copy(
                isSensorAvailable = false
            )
            return
        }

        compass.start()

        // Cancel old collectors to avoid leaks
        azimuthJob?.cancel()
        accuracyJob?.cancel()

        /**
         * Listen for device rotation (azimuth)
         */
        azimuthJob = viewModelScope.launch {
            compass.azimuth.collect { azimuth ->

                val aligned = isQiblaAligned(
                    qiblaBearing = _uiState.value.qiblaBearing,
                    deviceAzimuth = azimuth
                )

                // Reset vibration when misaligned
                if (!aligned) hasVibrated = false

                // Vibrate ONCE when aligned
                if (
                    aligned &&
                    !hasVibrated &&
                    _uiState.value.preferences.vibrationEnabled
                ) {
                    vibrateOnce(getApplication())
                    hasVibrated = true
                }

                _uiState.value = _uiState.value.copy(
                    deviceAzimuth = azimuth,
                    isAligned = aligned
                )
            }
        }

        /**
         * Listen for sensor accuracy updates
         */
        accuracyJob = viewModelScope.launch {
            compass.accuracy.collect { accuracy ->
                _uiState.value = _uiState.value.copy(
                    sensorAccuracy = accuracy
                )
            }
        }
    }

    /**
     * Stops compass and cancels collectors
     */
    fun stopCompass() {
        compass.stop()
        azimuthJob?.cancel()
        accuracyJob?.cancel()
    }

    /**
     * Updates Qibla bearing when user changes location manually
     */
    fun setUserLocation(lat: Double, lng: Double) {
        val bearing = calculateQiblaBearing(lat, lng)
        _uiState.value = _uiState.value.copy(
            qiblaBearing = bearing
        )
    }

    /**
     * Vibrates device once (safe & short)
     */
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

    /**
     * Updates user preferences and persists them
     */
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
