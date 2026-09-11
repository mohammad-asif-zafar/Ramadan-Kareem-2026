package com.hathway.ramadankareem2026.ui.ramadan

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.ramadankareem2026.core.util.toHijriDate
import com.hathway.ramadankareem2026.ui.components.CountdownCircularProgress
import com.hathway.ramadankareem2026.ui.ramadan.model.FastingDayStatus
import com.hathway.ramadankareem2026.ui.ramadan.model.RamadanDayUiModel
import com.hathway.ramadankareem2026.ui.theme.Emerald
import androidx.compose.ui.tooling.preview.Preview
import java.time.LocalDate
import java.time.LocalTime
import androidx.compose.animation.core.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.theme.Gold


@Composable
fun RamadanDayCard(
    day: RamadanDayUiModel, modifier: Modifier = Modifier, onClick: (RamadanDayUiModel) -> Unit
) {
    val isToday = day.status == FastingDayStatus.TODAY || day.status == FastingDayStatus.FASTING

    val hijri = remember(day.date) {
        day.date.toHijriDate()
    }
    val backgroundColor = when (day.status) {
        FastingDayStatus.TODAY,
        FastingDayStatus.FASTING -> Color(0xFFE8F5E9)
        FastingDayStatus.COMPLETED -> Color(0xFFF5F5F5)
        FastingDayStatus.UPCOMING -> Color(0xFFF0F0F5)
    }

    Card(
        modifier = modifier
            .aspectRatio(1f)
            .shadow(
                elevation = if (isToday) 12.dp else 2.dp,
                shape = RoundedCornerShape(24.dp),
                ambientColor = if (isToday) Emerald.copy(alpha = 0.4f) else Color.Black.copy(alpha = 0.1f),
                spotColor = if (isToday) Emerald.copy(alpha = 0.4f) else Color.Black.copy(alpha = 0.1f)
            )
            .clickable { onClick(day) }
            .border(
                width = if (isToday) 2.dp else 0.dp,
                color = Emerald,
                shape = RoundedCornerShape(24.dp)
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (androidx.compose.foundation.isSystemInDarkTheme()) {
                if (isToday) Color(0xFF1B2D27) else Color(0xFF1B252E)
            } else {
                backgroundColor
            }
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.ramadan_day, day.ramadanDay),
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp
                ),
                color = Gold
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = day.date.dayOfMonth.toString(),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = if (androidx.compose.foundation.isSystemInDarkTheme()) Color.White else Color(0xFF101820)
                )

                Text(
                    text = day.weekday.uppercase() + " " + day.month,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    color = if (androidx.compose.foundation.isSystemInDarkTheme()) Color.White.copy(alpha = 0.5f) else Color.Gray,
                    textAlign = TextAlign.Center
                )
            }

            Text(
                text = "${hijri.day} ${hijri.month}",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = Emerald,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            if (isToday) {
                DayStatusSection(day)
            } else if (day.status == FastingDayStatus.COMPLETED) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Emerald,
                    modifier = Modifier.size(14.dp)
                )
            } else {
                Spacer(modifier = Modifier.height(14.dp))
            }
        }
    }
}

@Composable
private fun DayStatusSection(day: RamadanDayUiModel) {
    when (day.status) {

        FastingDayStatus.FASTING,
        FastingDayStatus.TODAY -> {
            CountdownCircularProgress(
                totalMinutes = day.totalMinutes,
                remainingMinutes = day.remainingMinutes,
                modifier = Modifier.size(56.dp)
            )
        }

        FastingDayStatus.UPCOMING -> {
            StatusLabel(
                text = stringResource(R.string.upcoming),
                color = Color.Gray
            )
        }

        FastingDayStatus.COMPLETED -> {
            StatusLabel(
                text = stringResource(R.string.completed),
                color = Color(0xFF2E7D32)
            )
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



