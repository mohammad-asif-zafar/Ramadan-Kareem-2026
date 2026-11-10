package com.hathway.ramadankareem2026.ui.prayer.data.model

data class AladhanCalendarResponse(
    val code: Int,
    val status: String,
    val data: List<CalendarDayData>
)

data class CalendarDayData(
    val gregorian: GregorianDateInfo,
    val hijri: HijriDateInfo
)

data class GregorianDateInfo(
    val date: String,
    val day: String,
    val weekday: WeekdayInfo,
    val month: MonthInfo,
    val year: String
)

data class HijriDateInfo(
    val date: String,
    val day: String,
    val weekday: WeekdayInfo,
    val month: MonthInfo,
    val year: String,
    val designation: DesignationInfo,
    val holidays: List<String>
)

data class WeekdayInfo(
    val en: String,
    val ar: String?
)

data class MonthInfo(
    val number: Int,
    val en: String,
    val ar: String?
)

data class DesignationInfo(
    val abbreviated: String,
    val expanded: String
)
