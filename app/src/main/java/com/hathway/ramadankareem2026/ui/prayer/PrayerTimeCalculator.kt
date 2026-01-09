package com.hathway.ramadankareem2026.ui.prayer

import com.batoulapps.adhan.CalculationMethod
import com.batoulapps.adhan.CalculationParameters
import com.batoulapps.adhan.Coordinates
import com.batoulapps.adhan.PrayerTimes
import com.batoulapps.adhan.data.DateComponents
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

class PrayerTimeCalculator {

    fun calculate(
        lat: Double,
        lng: Double,
        method: CalculationMethod = CalculationMethod.MUSLIM_WORLD_LEAGUE
    ): PrayerTimeUiState {

        val coordinates = Coordinates(lat, lng)
        val params: CalculationParameters = method.parameters

        val today = LocalDate.now()
        val date = DateComponents(
            today.year,
            today.monthValue,
            today.dayOfMonth
        )

        val prayerTimes = PrayerTimes(
            coordinates,
            date,
            params
        )

        return PrayerTimeUiState(
            fajr = toLocalTime(prayerTimes.fajr),
            sunrise = toLocalTime(prayerTimes.sunrise),
            dhuhr = toLocalTime(prayerTimes.dhuhr),
            asr = toLocalTime(prayerTimes.asr),
            maghrib = toLocalTime(prayerTimes.maghrib),
            isha = toLocalTime(prayerTimes.isha)
        )
    }

    private fun toLocalTime(date: java.util.Date): LocalTime {
        return date.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalTime()
    }
}
