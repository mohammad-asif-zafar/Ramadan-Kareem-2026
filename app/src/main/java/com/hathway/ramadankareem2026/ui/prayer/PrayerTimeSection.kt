package com.hathway.ramadankareem2026.ui.prayer

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.format.DateTimeFormatter

@Composable
fun PrayerTimeSection(
    viewModel: PrayerViewModel = viewModel()
) {
    val state by viewModel.prayerState.collectAsState()

    val formatter = DateTimeFormatter.ofPattern("hh:mm a")

    if (state == null) return

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        PrayerRow("Fajr", state!!.fajr.format(formatter))
        PrayerRow("Sunrise", state!!.sunrise.format(formatter))
        PrayerRow("Dhuhr", state!!.dhuhr.format(formatter))
        PrayerRow("Asr", state!!.asr.format(formatter))
        PrayerRow("Maghrib", state!!.maghrib.format(formatter))
        PrayerRow("Isha", state!!.isha.format(formatter))
    }
}

@Composable
private fun PrayerRow(name: String, time: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(name)
        Text(time)
    }
}
