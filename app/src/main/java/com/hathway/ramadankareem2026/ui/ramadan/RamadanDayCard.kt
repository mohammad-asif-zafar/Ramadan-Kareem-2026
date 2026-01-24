package com.hathway.ramadankareem2026.ui.ramadan

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.ramadankareem2026.core.util.rememberLiveRemainingMinutes
import com.hathway.ramadankareem2026.core.util.toHijriDate
import com.hathway.ramadankareem2026.ui.components.CountdownCircularProgress
import com.hathway.ramadankareem2026.ui.ramadan.model.FastingDayStatus
import com.hathway.ramadankareem2026.ui.ramadan.model.RamadanDayUiModel
import com.hathway.ramadankareem2026.ui.theme.RamadanGreen
import androidx.compose.ui.tooling.preview.Preview
import java.time.LocalDate
import java.time.LocalTime
import androidx.compose.animation.core.*
import androidx.compose.runtime.getValue


@Composable
fun RamadanDayCard(
    day: RamadanDayUiModel, modifier: Modifier = Modifier, onClick: (RamadanDayUiModel) -> Unit
) {
    val isToday = day.status == FastingDayStatus.TODAY || day.status == FastingDayStatus.FASTING

    val pulseAlpha by animateFloatAsState(
        targetValue = if (isToday) 0.35f else 0f, animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing), repeatMode = RepeatMode.Reverse
        ), label = "TodayPulse"
    )

    val hijri = remember(day.date) {
        day.date.toHijriDate()
    }
    val backgroundColor = when (day.status) {
        FastingDayStatus.TODAY, FastingDayStatus.FASTING -> Color(0xFFE8F5E9)

        FastingDayStatus.COMPLETED -> Color(0xFFF5F5F5)
        FastingDayStatus.UPCOMING -> Color(0xFFF0F0F5)
    }

    val elevation = if (isToday) 8.dp else 2.dp

    Card(
        modifier = modifier
            .aspectRatio(1f)
            .background(
                if (isToday) RamadanGreen.copy(alpha = pulseAlpha)
                else Color.Transparent, shape = RoundedCornerShape(20.dp)
            )
            .padding(2.dp) // glow spacing
        .clickable { onClick(day) }
            .border(
                width = if (isToday) 2.dp else 0.dp,
                color = RamadanGreen,
                shape = RoundedCornerShape(18.dp)
            ),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(elevation)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Day ${day.ramadanDay}", fontSize = 11.sp, color = Color.Gray
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = day.weekday.uppercase(),
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF2E7D32)
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = day.date.dayOfMonth.toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = day.month, fontSize = 11.sp, color = Color.Gray
            )
            // 🌙 Hijri date (NEW)
            Text(
                text = "${hijri.day} ${hijri.month}",
                fontSize = 10.sp,
                color = RamadanGreen,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.height(8.dp))

            DayStatusSection(day)
        }
    }
}

@Composable
private fun DayStatusSection(day: RamadanDayUiModel) {
    when (day.status) {
        FastingDayStatus.FASTING, FastingDayStatus.TODAY -> {
            CountdownCircularProgress(
                totalMinutes = day.totalMinutes,
                remainingMinutes = day.remainingMinutes,
                modifier = Modifier.size(56.dp)
            )
        }

        FastingDayStatus.UPCOMING -> {
            StatusLabel("Upcoming", Color.Gray)
        }

        FastingDayStatus.COMPLETED -> {
            StatusLabel("Completed", Color(0xFF2E7D32))
        }
    }
}

@Composable
fun StatusLabel(text: String, color: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color.copy(alpha = 0.15f))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(text, fontSize = 11.sp, color = color)
    }
}

private fun previewRamadanDayUiModel(
    status: FastingDayStatus
): RamadanDayUiModel {
    return RamadanDayUiModel(
        ramadanDay = 5,
        date = LocalDate.of(2026, 3, 5),
        weekday = "Thu",
        month = "March",
        imsak = LocalTime.of(5, 30),
        fajr = LocalTime.of(5, 40),
        maghrib = LocalTime.of(18, 45),
        status = status,
        totalMinutes = 780,
        remainingMinutes = 150
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewRamadanDayCard_Fasting() {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .size(160.dp)
    ) {
        RamadanDayCard(
            day = previewRamadanDayUiModel(FastingDayStatus.FASTING), onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewRamadanDayCard_Today() {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .size(160.dp)
    ) {
        RamadanDayCard(
            day = previewRamadanDayUiModel(FastingDayStatus.TODAY), onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewRamadanDayCard_Upcoming() {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .size(160.dp)
    ) {
        RamadanDayCard(
            day = previewRamadanDayUiModel(FastingDayStatus.UPCOMING), onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewRamadanDayCard_Completed() {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .size(160.dp)
    ) {
        RamadanDayCard(
            day = previewRamadanDayUiModel(FastingDayStatus.COMPLETED), onClick = {})
    }
}



