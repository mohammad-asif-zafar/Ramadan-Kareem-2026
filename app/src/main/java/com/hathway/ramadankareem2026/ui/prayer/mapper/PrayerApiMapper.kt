package com.hathway.ramadankareem2026.ui.prayer.mapper

import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeUiState
import com.hathway.ramadankareem2026.ui.prayer.data.model.PrayerApiResponse
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * Maps API response → UI state
 *
 * All parsing logic lives here.
 */
object PrayerApiMapper {

    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    private val gregorianFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    fun map(response: PrayerApiResponse): PrayerTimeUiState {

        val timings = response.data.timings
        val hijri = response.data.date.hijri
        val gregorian = response.data.date.gregorian

        return PrayerTimeUiState(
            fajr = LocalTime.parse(timings.Fajr, timeFormatter),
            sunrise = LocalTime.parse(timings.Sunrise, timeFormatter),
            dhuhr = LocalTime.parse(timings.Dhuhr, timeFormatter),
            asr = LocalTime.parse(timings.Asr, timeFormatter),
            maghrib = LocalTime.parse(timings.Maghrib, timeFormatter),
            isha = LocalTime.parse(timings.Isha, timeFormatter),

            hijriDate = "${hijri.day} ${hijri.month.en} ${hijri.year} AH",
            gregorianDate = LocalDate.parse(gregorian.date, gregorianFormatter)
        )
    }
}
