package com.hathway.ramadankareem2026.ui.ramadan.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.ramadankareem2026.core.time.AppClock
import com.hathway.ramadankareem2026.core.time.SystemAppClock
import com.hathway.ramadankareem2026.core.util.NetworkUtil
import com.hathway.ramadankareem2026.core.util.minuteTicker
import com.hathway.ramadankareem2026.ui.prayer.data.PrayerRepository
import com.hathway.ramadankareem2026.ui.ramadan.logic.RamadanMonthlyOverviewCalculator
import com.hathway.ramadankareem2026.ui.ramadan.model.RamadanDayUiModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime

class RamadanCalendarViewModel(
    application: Application,
    private val clock: AppClock = SystemAppClock(),
    private val prayerRepository: PrayerRepository? = null
) : AndroidViewModel(application) {

    private val TAG = "RamadanCalendarViewModel"

    private val _days = MutableStateFlow<List<RamadanDayUiModel>>(emptyList())
    val days: StateFlow<List<RamadanDayUiModel>> = _days.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _currentLocation = MutableStateFlow<Pair<Double, Double>?>(null)
    val currentLocation: StateFlow<Pair<Double, Double>?> = _currentLocation.asStateFlow()

    init {
        loadCalendarData()
        scheduleMidnightRefresh()
        scheduleHourlyUpdates()
    }

    fun updateLocation(latitude: Double, longitude: Double) {
        _currentLocation.value = Pair(latitude, longitude)
        loadCalendarData()
    }

    private fun loadCalendarData() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                
                val now = clock.now()
                val ramadanStartDate = LocalDate.of(2026, 2, 18) // February 18, 2026
                val totalDays = 30

                // Default prayer times
                var fajrTime = LocalTime.of(5, 30)
                var maghribTime = LocalTime.of(19, 10)

                // Try to get dynamic prayer times from API if location is available
                _currentLocation.value?.let { (lat, lng) ->
                    if (NetworkUtil.isConnected(getApplication())) {
                        try {
                            Log.d(TAG, "Loading prayer times for location: $lat, $lng")
                            
                            // Get prayer times for today to establish pattern
                            val today = LocalDate.now()
                            val prayerTimes = prayerRepository?.load(lat, lng)
                            
                            prayerTimes?.firstOrNull { it.name.contains("Fajr", ignoreCase = true) }?.let { fajrPrayer ->
                                fajrTime = fajrPrayer.time
                            }
                            
                            prayerTimes?.firstOrNull { it.name.contains("Maghrib", ignoreCase = true) }?.let { maghribPrayer ->
                                maghribTime = maghribPrayer.time
                            }
                            
                            Log.d(TAG, "Dynamic prayer times - Fajr: $fajrTime, Maghrib: $maghribTime")
                        } catch (e: Exception) {
                            Log.w(TAG, "Failed to load dynamic prayer times, using defaults", e)
                        }
                    }
                }

                _days.value = RamadanMonthlyOverviewCalculator.calculate(
                    ramadanStartDate = ramadanStartDate,
                    totalDays = totalDays,
                    fajrTime = fajrTime,
                    maghribTime = maghribTime,
                    now = now
                )
                
                _isLoading.value = false
                Log.d(TAG, "Calendar data loaded with ${_days.value.size} days")
            } catch (e: Exception) {
                _error.value = "Failed to load calendar data: ${e.message}"
                _isLoading.value = false
                Log.e(TAG, "Error loading calendar data", e)
            }
        }
    }
    
    fun refresh() {
        loadCalendarData()
    }
    
    private fun scheduleMidnightRefresh() {
        viewModelScope.launch {
            while (true) {
                val now = clock.now()
                val nextMidnight = now.toLocalDate().plusDays(1).atStartOfDay()
                val delayMillis = Duration.between(now, nextMidnight).toMillis()
                
                delay(delayMillis)
                loadCalendarData()
            }
        }
    }
    
    private fun scheduleHourlyUpdates() {
        viewModelScope.launch {
            minuteTicker().collect {
                // Only update if we have data and it's currently Ramadan
                if (_days.value.isNotEmpty()) {
                    val now = clock.now()
                    val ramadanStartDate = LocalDate.of(2026, 2, 18)
                    val totalDays = 30

                    // Default prayer times
                    var fajrTime = LocalTime.of(5, 30)
                    var maghribTime = LocalTime.of(19, 10)

                    // Try to get dynamic prayer times
                    _currentLocation.value?.let { (lat, lng) ->
                        if (NetworkUtil.isConnected(getApplication())) {
                            try {
                                val prayerTimes = prayerRepository?.load(lat, lng)
                                
                                prayerTimes?.firstOrNull { it.name.contains("Fajr", ignoreCase = true) }?.let { fajrPrayer ->
                                    fajrTime = fajrPrayer.time
                                }
                                
                                prayerTimes?.firstOrNull { it.name.contains("Maghrib", ignoreCase = true) }?.let { maghribPrayer ->
                                    maghribTime = maghribPrayer.time
                                }
                            } catch (e: Exception) {
                                // Silently fail for real-time updates
                            }
                        }
                    }
                    
                    _days.value = RamadanMonthlyOverviewCalculator.calculate(
                        ramadanStartDate = ramadanStartDate,
                        totalDays = totalDays,
                        fajrTime = fajrTime,
                        maghribTime = maghribTime,
                        now = now
                    )
                }
            }
        }
    }
    
    fun clearError() {
        _error.value = null
    }
}
