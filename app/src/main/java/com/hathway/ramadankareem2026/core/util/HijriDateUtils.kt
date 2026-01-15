package com.hathway.ramadankareem2026.core.util

import java.time.LocalDate
import java.time.chrono.HijrahDate
import java.time.temporal.ChronoField

data class HijriDateUi(
    val day: Int, val month: String, val year: Int
)

fun LocalDate.toHijriDate(): HijriDateUi {
    val hijri = HijrahDate.from(this)

    val monthNumber = hijri.get(ChronoField.MONTH_OF_YEAR)

    return HijriDateUi(
        day = hijri.get(ChronoField.DAY_OF_MONTH),
        month = hijriMonthName(monthNumber),
        year = hijri.get(ChronoField.YEAR)
    )
}

private fun hijriMonthName(month: Int): String {
    return when (month) {
        1 -> "Muharram"
        2 -> "Safar"
        3 -> "Rabiʿ al-Awwal"
        4 -> "Rabiʿ al-Thani"
        5 -> "Jumada al-Awwal"
        6 -> "Jumada al-Thani"
        7 -> "Rajab"
        8 -> "Shaʿban"
        9 -> "Ramadan"
        10 -> "Shawwal"
        11 -> "Dhu al-Qiʿdah"
        12 -> "Dhu al-Hijjah"
        else -> ""
    }
}


