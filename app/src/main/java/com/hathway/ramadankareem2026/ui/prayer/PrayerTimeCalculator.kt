package com.hathway.ramadankareem2026.ui.prayer

import com.batoulapps.adhan.CalculationMethod
import com.batoulapps.adhan.Coordinates
import com.batoulapps.adhan.PrayerTimes
import com.batoulapps.adhan.data.DateComponents
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.chrono.HijrahDate
import java.time.temporal.ChronoField
import java.util.Date

class PrayerTimeCalculator {

    fun calculate(
        lat: Double, lng: Double, method: CalculationMethod = CalculationMethod.MUSLIM_WORLD_LEAGUE
    ): PrayerTimeUiState {

        val coordinates = Coordinates(lat, lng)
        val params = method.parameters

        val today = LocalDate.now()
        val date = DateComponents(
            today.year, today.monthValue, today.dayOfMonth
        )

        val prayerTimes = PrayerTimes(
            coordinates, date, params
        )

        val hijriDate = HijrahDate.now().let { date ->
            val day = date.get(ChronoField.DAY_OF_MONTH)
            val monthNumber = date.get(ChronoField.MONTH_OF_YEAR)
            val year = date.get(ChronoField.YEAR_OF_ERA)

            "$day ${hijriMonthName(monthNumber)} $year AH"
        }


        return PrayerTimeUiState(
            fajr = toLocalTime(prayerTimes.fajr),
            sunrise = toLocalTime(prayerTimes.sunrise),
            dhuhr = toLocalTime(prayerTimes.dhuhr),
            asr = toLocalTime(prayerTimes.asr),
            maghrib = toLocalTime(prayerTimes.maghrib),
            isha = toLocalTime(prayerTimes.isha),
            gregorianDate = LocalDate.now(),
            hijriDate = hijriDate
        )
    }

    private fun toLocalTime(date: Date?): LocalTime {
        return date?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalTime()
            ?: LocalTime.MIDNIGHT
    }
}

private fun hijriMonthName(month: Int): String = listOf(
    "Muharram",
    "Safar",
    "Rabi al-Awwal",
    "Rabi al-Thani",
    "Jumada al-Awwal",
    "Jumada al-Thani",
    "Rajab",
    "Sha'ban",
    "Ramadan",
    "Shawwal",
    "Dhul Qadah",
    "Dhul Hijjah"
)[month - 1]

