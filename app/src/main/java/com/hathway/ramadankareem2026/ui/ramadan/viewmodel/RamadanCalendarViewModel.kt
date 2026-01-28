package com.hathway.ramadankareem2026.ui.ramadan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.ramadankareem2026.core.time.AppClock
import com.hathway.ramadankareem2026.core.time.SystemAppClock
import com.hathway.ramadankareem2026.ui.ramadan.logic.RamadanMonthlyOverviewCalculator
import com.hathway.ramadankareem2026.ui.ramadan.model.RamadanDayUiModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class RamadanCalendarViewModel(
    private val clock: AppClock = SystemAppClock()
) : ViewModel() {

    private val _days = MutableStateFlow<List<RamadanDayUiModel>>(emptyList())
    val days: StateFlow<List<RamadanDayUiModel>> = _days.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadCalendarData()
        scheduleMidnightRefresh()
        scheduleHourlyUpdates()
    }

    private fun loadCalendarData() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                
                // Simulate API call delay
                delay(500)
                
                val now = clock.now()
                val ramadanStartDate = LocalDate.of(2026, 2, 18) // February 18, 2026
                val totalDays = 30
                val fajrTime = LocalTime.of(5, 30)
                val maghribTime = LocalTime.of(19, 10)

                _days.value = RamadanMonthlyOverviewCalculator.calculate(
                    ramadanStartDate = ramadanStartDate,
                    totalDays = totalDays,
                    fajrTime = fajrTime,
                    maghribTime = maghribTime,
                    now = now
                )
                
                _isLoading.value = false
            } catch (e: Exception) {
                _error.value = "Failed to load calendar data: ${e.message}"
                _isLoading.value = false
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
            while (true) {
                delay(60_000L) // Update every minute for real-time countdown
                
                // Only update if we have data and it's currently Ramadan
                if (_days.value.isNotEmpty()) {
                    val now = clock.now()
                    val ramadanStartDate = LocalDate.of(2026, 2, 18)
                    val totalDays = 30
                    val fajrTime = LocalTime.of(5, 30)
                    val maghribTime = LocalTime.of(19, 10)
                    
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
