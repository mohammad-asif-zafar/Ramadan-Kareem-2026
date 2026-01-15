package com.hathway.ramadankareem2026.ui.prayer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.ramadankareem2026.core.util.minuteTicker
import com.hathway.ramadankareem2026.ui.home.data.LocationDataStore
import com.hathway.ramadankareem2026.ui.prayer.mapper.CountdownResult
import com.hathway.ramadankareem2026.ui.prayer.mapper.PrayerCountdownMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PrayerViewModel(
    app: Application
) : AndroidViewModel(app) {

    private val calculator = PrayerTimeCalculator()
    private val locationStore = LocationDataStore(app)

    private val _prayerState = MutableStateFlow<PrayerTimeUiState?>(null)

    val prayerState: StateFlow<PrayerTimeUiState?> = _prayerState

    private val _countdown = MutableStateFlow<CountdownResult?>(null)

    val countdown: StateFlow<CountdownResult?> = _countdown

    init {
        loadPrayerTimes()
    }

    private fun loadPrayerTimes() {
        viewModelScope.launch {
            val location = locationStore.getLocation()
            if (
                location?.latitude != null &&
                location.longitude != null
            ) {
                val state = calculator.calculate(
                    location.latitude,
                    location.longitude
                )

                _prayerState.value = state

                // 🔁 LIVE countdown ticking every minute
                minuteTicker().collect {
                    _countdown.value =
                        PrayerCountdownMapper.nextPrayerCountdown(state)
                }
            }
        }
    }
}
