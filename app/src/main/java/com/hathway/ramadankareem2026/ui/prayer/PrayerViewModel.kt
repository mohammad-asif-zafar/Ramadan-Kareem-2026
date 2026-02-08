package com.hathway.ramadankareem2026.ui.prayer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
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
            _state.value?.let { prayerTimes ->
                PrayerNotificationScheduler.schedulePrayerNotifications(
                    getApplication(),
                    prayerTimes,
                    enabled
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


                runCatching {
                    repository.loadFromApi(formattedDate, lat, lng)
                }.onSuccess {
                    _state.value = it
                    
                    // Schedule prayer notifications if enabled
                    if (_notificationsEnabled.value) {
                        PrayerNotificationScheduler.schedulePrayerNotifications(
                            getApplication(),
                            it,
                            true
                        )
                    }
                }.onFailure {
                }
            }

            // 3️⃣ Start countdown
            startCountdown()
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
