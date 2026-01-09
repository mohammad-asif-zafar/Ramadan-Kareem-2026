package com.hathway.ramadankareem2026.ui.prayer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import com.hathway.ramadankareem2026.ui.home.model.PrayerTimeModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object PrayerTimeUiMapper {

    private val formatter = DateTimeFormatter.ofPattern("hh:mm a")

    fun map(state: PrayerTimeUiState): List<PrayerTimeModel> {
        val now = LocalTime.now()

        val prayers = listOf(
            PrayerTimeModel(
                "Fajr",
                state.fajr.format(formatter),
                Icons.Outlined.WbTwilight
            ),
            PrayerTimeModel(
                "Dhuhr",
                state.dhuhr.format(formatter),
                Icons.Outlined.LightMode
            ),
            PrayerTimeModel(
                "Asr",
                state.asr.format(formatter),
                Icons.Outlined.WbSunny
            ),
            PrayerTimeModel(
                "Maghrib",
                state.maghrib.format(formatter),
                Icons.Outlined.NightsStay
            ),
            PrayerTimeModel(
                "Isha",
                state.isha.format(formatter),
                Icons.Outlined.DarkMode
            )
        )

        val nextPrayerIndex =
            prayers.indexOfFirst {
                LocalTime.parse(it.time, formatter).isAfter(now)
            }.let { if (it == -1) 0 else it }

        return prayers.mapIndexed { index, prayer ->
            prayer.copy(isCurrent = index == nextPrayerIndex)
        }
    }
}
