package com.hathway.ramadankareem2026.ui.ramadan

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.ramadankareem2026.core.util.toHijriDate
import com.hathway.ramadankareem2026.ui.ramadan.model.FastingDayStatus
import com.hathway.ramadankareem2026.ui.ramadan.model.RamadanDayUiModel
import com.hathway.ramadankareem2026.ui.theme.Emerald
import androidx.compose.ui.tooling.preview.Preview
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun RamadanDayCard(
    day: RamadanDayUiModel,
    modifier: Modifier = Modifier,
    onClick: (RamadanDayUiModel) -> Unit
) {
    val isToday = day.status == FastingDayStatus.TODAY || day.status == FastingDayStatus.FASTING
    val isDark = MaterialTheme.colorScheme.background.luminance() < 0.5f

    val hijri = remember(day.date) {
        day.date.toHijriDate()
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .shadow(
                elevation = if (isToday) 8.dp else 2.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = if (isToday) Emerald.copy(alpha = 0.5f) else Color.Transparent,
                spotColor = if (isToday) Emerald.copy(alpha = 0.5f) else Color.Transparent
            )
            .clickable { onClick(day) }
            .border(
                width = if (isToday) 2.dp else 1.dp,
                color = if (isToday) Emerald else (if (isDark) Color.White.copy(alpha = 0.15f) else Color.Black.copy(alpha = 0.15f)),
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = when {
                isToday -> if (isDark) Color(0xFF13322B) else Color(0xFFE8F5E9)
                else -> if (isDark) Color(0xFF132220) else MaterialTheme.colorScheme.surfaceVariant
            }
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            // Crescent moon tucked into top-right corner
            Text(
                text = "🌙",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 2.dp, end = 2.dp),
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp)
            )

            // Centered Hijri Day & Gregorian Date
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = hijri.day.toString(),
                    maxLines = 1,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = if (isToday) (if (isDark) Emerald else Color(0xFF2E7D32)) else (if (isDark) Color.White else MaterialTheme.colorScheme.onSurface)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${day.date.dayOfMonth} ${day.month.take(3)}",
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    color = if (isDark) Color.White.copy(alpha = 0.7f) else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

private fun previewRamadanDayUiModel(
    status: FastingDayStatus
): RamadanDayUiModel {
    return RamadanDayUiModel(
        ramadanDay = 20,
        date = LocalDate.of(2027, 2, 27),
        weekday = "Thu",
        month = "February",
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
private fun PreviewRamadanDayCard_Today() {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .size(72.dp)
    ) {
        RamadanDayCard(
            day = previewRamadanDayUiModel(FastingDayStatus.TODAY),
            onClick = {}
        )
    }
}
