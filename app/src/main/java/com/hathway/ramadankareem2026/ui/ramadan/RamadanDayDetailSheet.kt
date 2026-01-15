package com.hathway.ramadankareem2026.ui.ramadan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.ramadankareem2026.core.util.toHijriDate
import com.hathway.ramadankareem2026.ui.components.CountdownCircularProgress
import com.hathway.ramadankareem2026.ui.ramadan.model.FastingDayStatus
import com.hathway.ramadankareem2026.ui.ramadan.model.RamadanDayUiModel
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalTime
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState

@Composable
fun RamadanDayDetailSheet(day: RamadanDayUiModel) {

    val hijri = remember(day.date) { day.date.toHijriDate() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {

        // Drag handle
        Box(
            modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color.LightGray.copy(alpha = 0.5f))
            )
        }

        Spacer(Modifier.height(16.dp))

        // Header
        Text(
            text = "Ramadan Day ${day.ramadanDay}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(4.dp))

        // English date
        Text(
            text = "${day.weekday}, ${day.date.dayOfMonth} ${day.month}",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        // 🌙 Hijri date (NEW)
        Text(
            text = "${hijri.day} ${hijri.month} ${hijri.year} AH",
            fontSize = 13.sp,
            color = Color(0xFF0F9D58),
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(12.dp))

        StatusChip(day.status)

        Spacer(Modifier.height(20.dp))

        // Countdown
        if (day.status == FastingDayStatus.FASTING || day.status == FastingDayStatus.TODAY) {

            val liveMinutes = rememberLiveRemainingMinutes(
                initialMinutes = day.remainingMinutes,
                isActive = day.status == FastingDayStatus.FASTING
            )

            Box(
                modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                CountdownCircularProgress(
                    totalMinutes = day.totalMinutes,
                    remainingMinutes = liveMinutes,
                    modifier = Modifier.size(120.dp)
                )
            }

            Spacer(Modifier.height(24.dp))
        }

        Text(
            text = "Prayer Times", fontSize = 16.sp, fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(12.dp))

        PrayerTimeCard("Imsak", day.imsak.toString())
        PrayerTimeCard("Fajr", day.fajr.toString())
        PrayerTimeCard("Maghrib", day.maghrib.toString())

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun StatusChip(status: FastingDayStatus) {
    val (text, color) = when (status) {
        FastingDayStatus.TODAY -> "Today" to Color(0xFFFF9800)
        FastingDayStatus.FASTING -> "Fasting" to Color(0xFF0F9D58)
        FastingDayStatus.UPCOMING -> "Upcoming" to Color.Gray
        FastingDayStatus.COMPLETED -> "Completed" to Color(0xFF0B6E3E)
    }

    Box(
        modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(color.copy(alpha = 0.15f))
                .padding(horizontal = 14.dp, vertical = 6.dp)
        ) {
            Text(text, color = color, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun PrayerTimeCard(label: String, time: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(1.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(label, color = Color.Gray)
            Text(time, fontWeight = FontWeight.SemiBold)
        }
    }
}

/* 🔴 LIVE COUNTDOWN */
@Composable
fun rememberLiveRemainingMinutes(
    initialMinutes: Int, isActive: Boolean
): Int {
    var minutes by remember { mutableStateOf(initialMinutes) }

    LaunchedEffect(isActive) {
        if (!isActive) return@LaunchedEffect
        while (minutes > 0) {
            delay(60_000)
            minutes -= 1
        }
    }
    return minutes
}

@Preview(showBackground = true)
@Composable
private fun PreviewRamadanDayDetailSheet() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp)
    ) {
        RamadanDayDetailSheet(
            day = previewRamadanDay()
        )
    }
}

private fun previewRamadanDay(): RamadanDayUiModel {
    return RamadanDayUiModel(
        ramadanDay = 5,
        date = LocalDate.of(2026, 3, 5),
        weekday = "Thu",
        month = "March",
        imsak = LocalTime.of(5, 30),
        fajr = LocalTime.of(5, 40),
        maghrib = LocalTime.of(18, 45),
        status = FastingDayStatus.FASTING,
        totalMinutes = 780,
        remainingMinutes = 150
    )
}