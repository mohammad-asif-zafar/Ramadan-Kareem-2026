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
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class RamadanCalendarViewModel(
    private val clock: AppClock = SystemAppClock()
) : ViewModel() {

    private val _days =
        MutableStateFlow<List<RamadanDayUiModel>>(emptyList())
    val days: StateFlow<List<RamadanDayUiModel>> = _days

    init {
        load()
        scheduleMidnightRefresh()
        loadCalendar()
    }

    private fun load() {
        val now = clock.now()

        // 🔒 Replace later with API / repository
        val ramadanStartDate = LocalDate.of(2026, 2, 18)
        val totalDays = 30
        val fajrTime = LocalTime.of(5, 30)
        val maghribTime = LocalTime.of(19, 10)

        _days.value =
            RamadanMonthlyOverviewCalculator.calculate(
                ramadanStartDate = ramadanStartDate,
                totalDays = totalDays,
                fajrTime = fajrTime,
                maghribTime = maghribTime,
                now = now
            )
    }
    private fun loadCalendar() {
        _days.value =
            RamadanMonthlyOverviewCalculator.calculate(
                ramadanStartDate = LocalDate.of(2026, 2, 18),
                totalDays = 30,
                fajrTime = LocalTime.of(5, 30),
                maghribTime = LocalTime.of(19, 10),
                now = LocalDateTime.now()
            )
    }

    private fun scheduleMidnightRefresh() {
        viewModelScope.launch {
            while (true) {
                val now = clock.now()
                val nextMidnight =
                    now.toLocalDate().plusDays(1).atStartOfDay()

                val delayMillis =
                    Duration.between(now, nextMidnight).toMillis()

                delay(delayMillis)
                load()
            }
        }
    }

    fun refresh() {
        load()
    }
}
