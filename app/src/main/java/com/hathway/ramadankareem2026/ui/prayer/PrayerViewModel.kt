package com.hathway.ramadankareem2026.ui.prayer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
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

    /**
     * ENTRY POINT from UI
     */
    fun load(lat: Double, lng: Double) {
        viewModelScope.launch {

            // 1️⃣ Always show demo immediately
           // _state.value = PrayerDemoData.demo()

            // 2️⃣ Load from API if internet exists
            if (NetworkUtil.isConnected(getApplication())) {

                val today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))

                runCatching {
                    repository.loadFromApi(today, lat, lng)
                }.onSuccess {
                    _state.value = it
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
