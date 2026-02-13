package com.hathway.ramadankareem2026.ui.prayer

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.ramadankareem2026.core.location.LocationUiState
import com.hathway.ramadankareem2026.core.service.PrayerNotificationScheduler
import com.hathway.ramadankareem2026.core.util.NetworkUtil
import com.hathway.ramadankareem2026.core.util.minuteTicker
import com.hathway.ramadankareem2026.ui.prayer.data.PrayerDemoData
import com.hathway.ramadankareem2026.ui.prayer.data.PrayerRepository
import com.hathway.ramadankareem2026.ui.prayer.mapper.CountdownResult
import com.hathway.ramadankareem2026.ui.prayer.mapper.PrayerCountdownMapper
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val TAG = "PrayerViewModel"

/**
 * PrayerViewModel
 *
 * SINGLE SOURCE OF TRUTH:
 * - PrayerTimeUiState
 *
 * FLOW:
 * Demo → API (if online) → Countdown
 */
class PrayerViewModel(
    app: Application, private val repository: PrayerRepository
) : AndroidViewModel(app) {


    /** 🔹 Single UI state (demo-safe) */
    private val _state = MutableStateFlow(PrayerDemoData.demo())
    val state: StateFlow<PrayerTimeUiState> = _state

    /** 🔹 Countdown to next prayer */
    private val _countdown = MutableStateFlow(
        CountdownResult("", 0)
    )
    val countdown: StateFlow<CountdownResult> = _countdown

    /** 🔹 Active countdown job */
    private var countdownJob: Job? = null

    /** 🔹 Prayer notifications enabled state */
    private val _notificationsEnabled = MutableStateFlow(true)
    val notificationsEnabled: StateFlow<Boolean> = _notificationsEnabled

    /**
     * Toggle prayer notifications
     */
    fun togglePrayerNotifications(enabled: Boolean) {
        _notificationsEnabled.value = enabled
        if (enabled) {
            // Schedule notifications for current prayer times
            _state.value.let { prayerTimes ->
                PrayerNotificationScheduler.schedulePrayerNotifications(
                    getApplication(),
                    prayerTimes,
                    true
                )
            }
        } else {
            // Cancel all prayer notifications
            PrayerNotificationScheduler.cancelAllPrayerNotifications(getApplication())
        }
    }

    /**
     * ENTRY POINT from UI
     */
    fun load(lat: Double, lng: Double, date: LocalDate = LocalDate.now()) {
        viewModelScope.launch {

            // 1️⃣ Always show demo immediately
           // _state.value = PrayerDemoData.demo()

            // 2️⃣ Load from API if internet exists
            if (NetworkUtil.isConnected(getApplication())) {

                // Format date once
                val formattedDate =
                    date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))

                Log.d(TAG, "Loading prayer times for date: $formattedDate, lat: $lat, lng: $lng")

                runCatching {
                    repository.loadFromApi(formattedDate, lat, lng)
                }.onSuccess { prayerTimes ->
                    Log.d(TAG, "Successfully loaded prayer times from API")
                    _state.value = prayerTimes
                    
                    // Schedule prayer notifications if enabled
                    if (_notificationsEnabled.value) {
                        PrayerNotificationScheduler.schedulePrayerNotifications(
                            getApplication(),
                            prayerTimes,
                            true
                        )
                    }
                }.onFailure { exception ->
                    Log.e(TAG, "Failed to load prayer times from API", exception)
                    // Keep demo data as fallback
                }
            } else {
                Log.w(TAG, "No internet connection, using demo data")
            }

            // 3️⃣ Start countdown
            startCountdown()
        }
    }
    
    /**
     * Load prayer times with location state for better timezone detection
     */
    fun loadWithLocation(locationState: LocationUiState, date: LocalDate = LocalDate.now()) {
        when (locationState) {
            is LocationUiState.Success -> {
                load(locationState.latitude, locationState.longitude, date)
            }
            is LocationUiState.Loading -> {
                Log.d(TAG, "Location still loading, using demo data")
            }
            is LocationUiState.Error -> {
                Log.w(TAG, "Location error: ${locationState.message}, using demo data")
            }
        }
    }

    /**
     * Starts live countdown to next prayer
     */
    private fun startCountdown() {
        countdownJob?.cancel()

        countdownJob = viewModelScope.launch {
            minuteTicker().collect {
                _countdown.value = PrayerCountdownMapper.nextPrayerCountdown(_state.value)
            }
        }
    }
}
