package com.hathway.ramadankareem2026.ui.prayer

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.ramadankareem2026.ui.prayer.data.PrayerViewModelFactory
import java.time.format.DateTimeFormatter

@Composable
fun PrayerTimeSection(

) {
    val context = LocalContext.current
    val app = context.applicationContext as Application

    val viewModel: PrayerViewModel = viewModel(
        factory = PrayerViewModelFactory(app)
    )

    // 🔹 Collect single source of truth from ViewModel
    val state by viewModel.state.collectAsState()

    // 🔹 Formatter for displaying prayer times
    val formatter = DateTimeFormatter.ofPattern("hh:mm a")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // 🔹 Display prayer times (UI unchanged)
        PrayerRow("Fajr", state.fajr.format(formatter))
        PrayerRow("Sunrise", state.sunrise.format(formatter))
        PrayerRow("Dhuhr", state.dhuhr.format(formatter))
        PrayerRow("Asr", state.asr.format(formatter))
        PrayerRow("Maghrib", state.maghrib.format(formatter))
        PrayerRow("Isha", state.isha.format(formatter))
    }
}

@Composable
private fun PrayerRow(
    name: String, time: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = name)
        Text(text = time)
    }
}
