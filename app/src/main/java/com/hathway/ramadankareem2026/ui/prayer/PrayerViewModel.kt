package com.hathway.ramadankareem2026.ui.prayer

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.ramadankareem2026.core.util.minuteTicker
import com.hathway.ramadankareem2026.ui.home.model.PrayerDomain
import com.hathway.ramadankareem2026.ui.prayer.data.PrayerRepository
import com.hathway.ramadankareem2026.ui.prayer.mapper.CountdownResult
import com.hathway.ramadankareem2026.ui.prayer.mapper.PrayerCountdownMapper
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.chrono.HijrahDate

/**
 * PrayerViewModel
 *
 * RESPONSIBILITY:
 * - Calculate prayer times based on latitude & longitude
 * - Expose prayer list for UI
 * - Maintain a live countdown to the next prayer
 *
 * IMPORTANT RULE:
 * ❌ This ViewModel does NOT own location
 * ✅ Location is passed in from HomeScreen
 */
class PrayerViewModel(
    app: Application
) : AndroidViewModel(app) {

    /** Calculates prayer times (pure logic) */
    private val calculator = PrayerTimeCalculator()

    /** Repository for loading prayer list (adhan / cache / demo) */
    private val repo = PrayerRepository(app)

    /**
     * Holds prayer timing UI state.
     *
     * We initialize with a DEMO state so UI never crashes.
     */
    private val _prayerState =
        MutableStateFlow(
            PrayerTimeUiState(
                fajr = LocalTime.of(6, 0),
                sunrise = LocalTime.of(7, 15),
                dhuhr = LocalTime.of(12, 30),
                asr = LocalTime.of(15, 45),
                maghrib = LocalTime.of(18, 10),
                isha = LocalTime.of(19, 30),
                gregorianDate = LocalDate.now(),
                hijriDate = "1 Ramadan 1447 AH" // ✅ DEMO STRING
            )
        )

    val prayerState: StateFlow<PrayerTimeUiState> = _prayerState

    /**
     * Countdown to next prayer.
     *
     * Initialized with safe placeholder values.
     */
    private val _countdown = MutableStateFlow(
        CountdownResult(
            prayerName = "", minutesRemaining = 0
        )
    )

    val countdown: StateFlow<CountdownResult> = _countdown

    /**
     * List of prayers displayed in UI
     * (Fajr, Dhuhr, Asr, Maghrib, Isha)
     */
    private val _prayers = MutableStateFlow<List<PrayerDomain>>(emptyList())
    val prayers: StateFlow<List<PrayerDomain>> = _prayers

    /**
     * Holds the active countdown coroutine
     *
     * Used to cancel old countdown when location changes
     */
    private var countdownJob: Job? = null

    private val TAG = "PrayerViewModel"

    /**
     * Called from HomeScreen whenever location changes.
     *
     * @param lat latitude of selected location
     * @param lng longitude of selected location
     *
     * FLOW:
     * 1. Load prayer list
     * 2. Calculate prayer timing state
     * 3. Start / restart countdown
     */
    fun load(lat: Double, lng: Double) {
        viewModelScope.launch {

            // 1️⃣ Load prayer times list (repository handles cache/demo)
            _prayers.value = repo.load(lat, lng)
            Log.d(TAG, "Loaded prayers: ${_prayers.value}")

            // 2️⃣ Calculate prayer timing logic
            val state = calculator.calculate(lat, lng)
            _prayerState.value = state

            // 3️⃣ Start live countdown safely
            startCountdown(state)
        }
    }

    /**
     * Starts a live countdown to the next prayer.
     *
     * IMPORTANT:
     * - Cancels previous ticker to avoid memory leaks
     * - Emits updates every minute
     */
    private fun startCountdown(state: PrayerTimeUiState) {

        // Cancel any previous countdown job
        countdownJob?.cancel()

        countdownJob = viewModelScope.launch {
            minuteTicker().collect {

                // Map current time → next prayer countdown
                _countdown.value = PrayerCountdownMapper.nextPrayerCountdown(state)
            }
        }
    }
}
