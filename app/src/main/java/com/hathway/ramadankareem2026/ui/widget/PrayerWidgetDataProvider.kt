package com.hathway.ramadankareem2026.ui.widget

import android.content.Context
import com.hathway.ramadankareem2026.core.location.LocationDataStore
import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeCalculator
import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeUiState
import com.hathway.ramadankareem2026.ui.prayer.mapper.PrayerCountdownMapper
import com.hathway.ramadankareem2026.ui.prayer.mapper.CountdownResult
import java.time.LocalDate
import java.time.LocalTime

/**
 * Comprehensive PrayerWidgetDataProvider
 *
 * PURPOSE:
 * - Provides rich, complete prayer data from app
 * - Includes all prayer times, fasting info, and context
 * - High-quality data for premium widget experience
 */
object PrayerWidgetDataProvider {

    fun load(context: Context): PrayerWidgetState {
        // 📍 Location data
        val locationStore = LocationDataStore(context.applicationContext)
        val location = locationStore.getBlocking()

        // 🧮 Calculate all prayer times using app's calculator
        val calculator = PrayerTimeCalculator()
        val prayerState = calculator.calculate(
            location.latitude, location.longitude
        )

        // ⏳ Get current and next prayer info
        val countdown = PrayerCountdownMapper.nextPrayerCountdown(prayerState)
        val currentPrayer = getCurrentPrayerInfo(prayerState)
        val nextPrayer = getNextPrayerInfo(prayerState, countdown)
        
        // 🕌 Ramadan and fasting information
        val isRamadan = isRamadanPeriod(prayerState.gregorianDate)
        val fastingStatus = calculateFastingStatus(prayerState, isRamadan)
        val ramadanDay = if (isRamadan) getRamadanDay(prayerState.gregorianDate) else null

        // 🧩 Build comprehensive widget state
        return PrayerWidgetState(
            // 🕌 All prayer times
            fajr = prayerState.fajr,
            sunrise = prayerState.sunrise,
            dhuhr = prayerState.dhuhr,
            asr = prayerState.asr,
            maghrib = prayerState.maghrib,
            isha = prayerState.isha,
            
            // 📍 Location
            city = location.city,
            country = location.country,
            
            // ⏰ Current and next prayer
            currentPrayer = currentPrayer,
            nextPrayer = nextPrayer,
            
            // 📅 Dates
            gregorianDate = prayerState.gregorianDate,
            hijriDate = prayerState.hijriDate,
            
            // 🌙 Ramadan info
            isRamadan = isRamadan,
            fastingStatus = fastingStatus,
            ramadanDay = ramadanDay,
            
            // 🌤️ Additional context (placeholders for now)
            currentTemperature = "24°C", // Could integrate weather API
            weatherCondition = "Partly Cloudy", // Could integrate weather API
            qiblaDirection = "247° NE", // Could calculate from location
            
            // ⏱️ Timing context
            timeUntilSunrise = calculateMinutesUntil(prayerState.sunrise),
            timeUntilSunset = calculateMinutesUntil(prayerState.maghrib),
            dayProgress = calculateDayProgress(prayerState)
        )
    }

    private fun getCurrentPrayerInfo(prayerState: PrayerTimeUiState): PrayerInfo {
        val now = LocalTime.now()
        val prayers = listOf(
            "Fajr" to prayerState.fajr,
            "Dhuhr" to prayerState.dhuhr,
            "Asr" to prayerState.asr,
            "Maghrib" to prayerState.maghrib,
            "Isha" to prayerState.isha
        )
        
        // Find current prayer (last prayer that started)
        val current = prayers.lastOrNull { (_, time) -> 
            !now.isBefore(time) 
        } ?: prayers.last()
        
        // Calculate time remaining until next prayer
        val nextPrayerIndex = prayers.indexOf(current) + 1
        val nextPrayerTime = if (nextPrayerIndex < prayers.size) {
            prayers[nextPrayerIndex].second
        } else {
            prayers.first().second.plusHours(24) // Tomorrow's first prayer
        }
        
        val minutesRemaining = java.time.Duration.between(now, nextPrayerTime).toMinutes().toInt()
        
        return PrayerInfo(
            name = current.first,
            arabicName = getArabicName(current.first),
            time = current.second,
            minutesRemaining = minutesRemaining,
            urgency = calculateUrgency(minutesRemaining),
            isPassed = true
        )
    }

    private fun getNextPrayerInfo(prayerState: PrayerTimeUiState, countdown: CountdownResult): PrayerInfo {
        return PrayerInfo(
            name = countdown.prayerName,
            arabicName = getArabicName(countdown.prayerName),
            time = getNextPrayerTime(prayerState, countdown.prayerName),
            minutesRemaining = countdown.minutesRemaining,
            urgency = calculateUrgency(countdown.minutesRemaining),
            isPassed = false
        )
    }

    private fun getArabicName(prayerName: String): String {
        return when (prayerName.lowercase()) {
            "fajr" -> "الفجر"
            "sunrise" -> "الشروق"
            "dhuhr" -> "الظهر"
            "asr" -> "العصر"
            "maghrib" -> "المغرب"
            "isha" -> "العشاء"
            else -> prayerName
        }
    }

    private fun getNextPrayerTime(prayerState: PrayerTimeUiState, prayerName: String): LocalTime {
        return when (prayerName.lowercase()) {
            "fajr" -> prayerState.fajr
            "sunrise" -> prayerState.sunrise
            "dhuhr" -> prayerState.dhuhr
            "asr" -> prayerState.asr
            "maghrib" -> prayerState.maghrib
            "isha" -> prayerState.isha
            else -> LocalTime.now()
        }
    }

    private fun calculateUrgency(minutesRemaining: Int): PrayerUrgency {
        return when {
            minutesRemaining <= 15 -> PrayerUrgency.URGENT
            minutesRemaining <= 30 -> PrayerUrgency.SOON
            else -> PrayerUrgency.NORMAL
        }
    }

    private fun calculateFastingStatus(prayerState: PrayerTimeUiState, isRamadan: Boolean): FastingStatus {
        if (!isRamadan) return FastingStatus.NOT_RAMADAN
        
        val now = LocalTime.now()
        return when {
            now.isBefore(prayerState.fajr) -> FastingStatus.SUHOOR_TIME
            now.isAfter(prayerState.maghrib) -> FastingStatus.NOT_RAMADAN
            now.isBefore(prayerState.maghrib) -> FastingStatus.FASTING
            else -> FastingStatus.IFTAR_TIME
        }
    }

    private fun isRamadanPeriod(date: LocalDate): Boolean {
        // Ramadan 2026: February 18 - March 19
        val ramadanStart = LocalDate.of(2026, 2, 18)
        val ramadanEnd = LocalDate.of(2026, 3, 19)
        return !date.isBefore(ramadanStart) && !date.isAfter(ramadanEnd)
    }

    private fun getRamadanDay(date: LocalDate): Int {
        val ramadanStart = LocalDate.of(2026, 2, 18)
        return date.dayOfYear - ramadanStart.dayOfYear + 1
    }

    private fun calculateMinutesUntil(time: LocalTime): Int {
        val now = LocalTime.now()
        val target = if (now.isAfter(time)) {
            time.plusHours(24) // Tomorrow
        } else {
            time
        }
        return java.time.Duration.between(now, target).toMinutes().toInt()
    }

    private fun calculateDayProgress(prayerState: PrayerTimeUiState): Float {
        val now = LocalTime.now()
        val dayStart = prayerState.fajr
        val dayEnd = prayerState.isha
        
        val totalMinutes = java.time.Duration.between(dayStart, dayEnd).toMinutes().toFloat()
        val elapsedMinutes = java.time.Duration.between(dayStart, now).toMinutes().toFloat()
        
        return (elapsedMinutes / totalMinutes).coerceIn(0f, 1f)
    }
}

