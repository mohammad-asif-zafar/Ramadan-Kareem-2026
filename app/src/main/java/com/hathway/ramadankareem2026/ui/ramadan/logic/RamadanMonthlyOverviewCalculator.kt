package com.hathway.ramadankareem2026.ui.ramadan.logic

import com.hathway.ramadankareem2026.ui.ramadan.model.FastingDayStatus
import com.hathway.ramadankareem2026.ui.ramadan.model.RamadanDayUiModel
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.Locale

object RamadanMonthlyOverviewCalculator {

    fun calculate(
        ramadanStartDate: LocalDate,
        totalDays: Int,
        fajrTime: LocalTime,
        maghribTime: LocalTime,
        now: LocalDateTime,
        imsakOffsetMinutes: Long = 10
    ): List<RamadanDayUiModel> {

        return (0 until totalDays).map { index ->

            val date = ramadanStartDate.plusDays(index.toLong())
            val ramadanDay = index + 1

            val imsak = fajrTime.minusMinutes(imsakOffsetMinutes)

            val fajrDateTime = LocalDateTime.of(date, fajrTime)
            val maghribDateTime = LocalDateTime.of(date, maghribTime)

            val totalMinutes =
                Duration.between(fajrDateTime, maghribDateTime).toMinutes().toInt()

            val status = when {
                date.isAfter(now.toLocalDate()) -> FastingDayStatus.UPCOMING
                date.isBefore(now.toLocalDate()) -> FastingDayStatus.COMPLETED
                now.isBefore(fajrDateTime) -> FastingDayStatus.TODAY
                now.isBefore(maghribDateTime) -> FastingDayStatus.FASTING
                else -> FastingDayStatus.COMPLETED
            }

            val remainingMinutes = when (status) {
                FastingDayStatus.FASTING ->
                    Duration.between(now, maghribDateTime).toMinutes().toInt()

                FastingDayStatus.TODAY ->
                    Duration.between(fajrDateTime, maghribDateTime).toMinutes().toInt()

                else -> 0
            }.coerceAtLeast(0)

            RamadanDayUiModel(
                ramadanDay = ramadanDay,
                date = date,
                weekday = date.dayOfWeek.getDisplayName(
                    TextStyle.SHORT,
                    Locale.ENGLISH
                ),
                month = date.month.getDisplayName(
                    TextStyle.FULL,
                    Locale.ENGLISH
                ),
                imsak = imsak,
                fajr = fajrTime,
                maghrib = maghribTime,
                status = status,
                totalMinutes = totalMinutes,
                remainingMinutes = remainingMinutes
            )
        }
    }
}
