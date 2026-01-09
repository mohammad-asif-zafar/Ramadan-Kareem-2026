package com.hathway.ramadankareem2026.ui.prayer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.ramadankareem2026.ui.home.data.LocationDataStore
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

    init {
        loadPrayerTimes()
    }

    private fun loadPrayerTimes() {
        viewModelScope.launch {
            val location = locationStore.getLocation()
            if (location?.latitude != null && location.longitude != null) {
                _prayerState.value = calculator.calculate(
                    location.latitude, location.longitude
                )
            }
        }
    }
}
