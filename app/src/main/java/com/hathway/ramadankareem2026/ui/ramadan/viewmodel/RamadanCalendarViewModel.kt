package com.hathway.ramadankareem2026.ui.ramadan.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.ramadankareem2026.core.time.AppClock
import com.hathway.ramadankareem2026.core.time.SystemAppClock
import com.hathway.ramadankareem2026.core.util.NetworkUtil
import com.hathway.ramadankareem2026.ui.prayer.PrayerType
import com.hathway.ramadankareem2026.ui.prayer.data.PrayerRepository
import com.hathway.ramadankareem2026.ui.prayer.data.api.NetworkModule
import com.hathway.ramadankareem2026.ui.ramadan.logic.RamadanMonthlyOverviewCalculator
import com.hathway.ramadankareem2026.ui.ramadan.model.FastingDayStatus
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

    private val _days = MutableStateFlow<List<RamadanDayUiModel>>(emptyList())
    val days: StateFlow<List<RamadanDayUiModel>> = _days.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _isHijriMode = MutableStateFlow(true)
    val isHijriMode: StateFlow<Boolean> = _isHijriMode.asStateFlow()

    private val _currentMonthYearText = MutableStateFlow("Ramadan 1448 هـ")
    val currentMonthYearText: StateFlow<String> = _currentMonthYearText.asStateFlow()

    private val _monthSubtitleText = MutableStateFlow("February – March 2027")
    val monthSubtitleText: StateFlow<String> = _monthSubtitleText.asStateFlow()

    private var currentYear = LocalDate.now().year
    private var currentMonth = LocalDate.now().monthValue

    private val _currentLocation = MutableStateFlow<Pair<Double, Double>?>(null)

    init {
        loadCalendarData()
        scheduleMidnightRefresh()
    }

    fun setCalendarMode(isHijri: Boolean) {
        _isHijriMode.value = isHijri
        loadCalendarData()
    }

    fun nextMonth() {
        currentMonth++
        if (currentMonth > 12) {
            currentMonth = 1
            currentYear++
        }
        loadCalendarData()
    }

    fun previousMonth() {
        currentMonth--
        if (currentMonth < 1) {
            currentMonth = 12
            currentYear--
        }
        loadCalendarData()
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
                val currentDate = now.toLocalDate()

                // Default prayer times
                var fajrTime = LocalTime.of(5, 30)
                var maghribTime = LocalTime.of(19, 10)

                _currentLocation.value?.let { (lat, lng) ->
                    if (NetworkUtil.isConnected(getApplication())) {
                        try {
                            val prayerTimes = prayerRepository?.load(lat, lng)
                            prayerTimes?.firstOrNull { it.type == PrayerType.FAJR }?.let { fajrTime = it.time }
                            prayerTimes?.firstOrNull { it.type == PrayerType.MAGHRIB }?.let { maghribTime = it.time }
                        } catch (e: Exception) {}
                    }
                }

                var apiLoaded = false
                if (NetworkUtil.isConnected(getApplication())) {
                    try {
                        val calendarResponse = if (_isHijriMode.value) {
                            NetworkModule.prayerApi.getHijriToGregorianCalendar(currentMonth, currentYear)
                        } else {
                            NetworkModule.prayerApi.getGregorianToHijriCalendar(currentMonth, currentYear)
                        }

                        if (calendarResponse.code == 200 && calendarResponse.data.isNotEmpty()) {
                            val firstDay = calendarResponse.data.first()
                            val lastDay = calendarResponse.data.last()

                            if (_isHijriMode.value) {
                                _currentMonthYearText.value = "${firstDay.hijri.month.en} ${firstDay.hijri.year} هـ"
                                _monthSubtitleText.value = "${firstDay.gregorian.month.en} – ${lastDay.gregorian.month.en} $currentYear"
                            } else {
                                _currentMonthYearText.value = "${firstDay.gregorian.month.en} $currentYear"
                                _monthSubtitleText.value = "${firstDay.hijri.month.en} – ${lastDay.hijri.month.en} ${firstDay.hijri.year} هـ"
                            }

                            val mappedDays = calendarResponse.data.mapIndexed { index, dayData ->
                                val dateStr = dayData.gregorian.date // format DD-MM-YYYY
                                val parts = dateStr.split("-")
                                val gDate = if (parts.size == 3) {
                                    try {
                                        LocalDate.of(parts[2].toInt(), parts[1].toInt(), parts[0].toInt())
                                    } catch (e: Exception) {
                                        currentDate.withDayOfMonth(index + 1)
                                    }
                                } else {
                                    currentDate.withDayOfMonth(index + 1)
                                }

                                val hijriDay = dayData.hijri.day.toIntOrNull() ?: (index + 1)
                                val weekday = dayData.gregorian.weekday.en.take(3)
                                val monthName = dayData.gregorian.month.en

                                val status = when {
                                    gDate.isEqual(currentDate) -> FastingDayStatus.TODAY
                                    gDate.isBefore(currentDate) -> FastingDayStatus.COMPLETED
                                    else -> FastingDayStatus.UPCOMING
                                }

                                RamadanDayUiModel(
                                    ramadanDay = hijriDay,
                                    date = gDate,
                                    weekday = weekday,
                                    month = monthName,
                                    imsak = LocalTime.of(5, 30),
                                    fajr = fajrTime,
                                    maghrib = maghribTime,
                                    status = status,
                                    totalMinutes = 780,
                                    remainingMinutes = if (status == FastingDayStatus.TODAY) 150 else 0
                                )
                            }
                            _days.value = mappedDays
                            apiLoaded = true
                        }
                    } catch (e: Exception) {
                        // Fallback
                    }
                }

                if (!apiLoaded) {
                    val ramadanStartDate = LocalDate.of(2027, 2, 8)
                    val totalDays = 30
                    _days.value = RamadanMonthlyOverviewCalculator.calculate(
                        ramadanStartDate = ramadanStartDate,
                        totalDays = totalDays,
                        fajrTime = fajrTime,
                        maghribTime = maghribTime,
                        now = now
                    )
                }
                
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
    
    fun clearError() {
        _error.value = null
    }
}
