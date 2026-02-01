package com.hathway.ramadankareem2026.ui.widget

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.LocalContext
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.*
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.unit.ColorProvider
import androidx.glance.text.TextStyle
import androidx.glance.Image
import androidx.glance.ImageProvider
import com.hathway.ramadankareem2026.R

class PrayerCountdownWidget : GlanceAppWidget() {

    override suspend fun provideGlance(
        context: Context,
        id: GlanceId
    ) {
        provideContent {
            HighQualityWidgetContent()
        }
    }

    @Composable
    private fun HighQualityWidgetContent() {

        val context = LocalContext.current
        val state = PrayerWidgetDataProvider.load(context)

        // Simple and clean UI design with icons
        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(ColorProvider(Color.White))
                .padding(12.dp)
                .clickable(actionStartActivity())
        ) {
            
            // Clean location header with icon
            Row(
                verticalAlignment = androidx.glance.layout.Alignment.CenterVertically
            ) {
                Image(
                    provider = ImageProvider(R.drawable.ic_kaaba),
                    contentDescription = "Location",
                    modifier = GlanceModifier.size(16.dp)
                )
                Spacer(modifier = GlanceModifier.width(4.dp))
                Text(
                    text = "${state.city}",
                    style = TextStyle(
                        color = ColorProvider(Color(0xFF0F9D58)), // Ramadan Green
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = GlanceModifier.height(8.dp))

            // Current prayer with icon
            Row(
                verticalAlignment = androidx.glance.layout.Alignment.CenterVertically
            ) {
                Image(
                    provider = ImageProvider(R.drawable.ic_kaaba),
                    contentDescription = "Current Prayer",
                    modifier = GlanceModifier.size(18.dp)
                )
                Spacer(modifier = GlanceModifier.width(6.dp))
                Column {
                    Text(
                        text = "${state.currentPrayer.name}",
                        style = TextStyle(
                            color = ColorProvider(Color.Black),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = formatTimeRemaining(state.currentPrayer.minutesRemaining),
                        style = TextStyle(
                            color = ColorProvider(Color(0xFF666666)), // Clean gray
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
            }

            Spacer(modifier = GlanceModifier.height(6.dp))

            // Iftar/Next prayer with icon
            Row(
                verticalAlignment = androidx.glance.layout.Alignment.CenterVertically
            ) {
                if (state.isRamadan) {
                    Image(
                        provider = ImageProvider(R.drawable.ic_kaaba),
                        contentDescription = "Iftar",
                        modifier = GlanceModifier.size(14.dp)
                    )
                    Spacer(modifier = GlanceModifier.width(4.dp))
                    Text(
                        text = "Iftar: ${formatTimeRemaining(state.timeUntilSunset ?: 0)}",
                        style = TextStyle(
                            color = ColorProvider(Color(0xFFD4AF37)), // Ramadan Gold
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                } else {
                    Image(
                        provider = ImageProvider(R.drawable.ic_kaaba),
                        contentDescription = "Next Prayer",
                        modifier = GlanceModifier.size(14.dp)
                    )
                    Spacer(modifier = GlanceModifier.width(4.dp))
                    Text(
                        text = "Next: ${state.nextPrayer.name} in ${formatTimeRemaining(state.nextPrayer.minutesRemaining)}",
                        style = TextStyle(
                            color = ColorProvider(Color(0xFF666666)),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
            }

            Spacer(modifier = GlanceModifier.height(4.dp))

            // Suhoor with icon - only during Ramadan
            if (state.isRamadan) {
                Row(
                    verticalAlignment = androidx.glance.layout.Alignment.CenterVertically
                ) {
                    Image(
                        provider = ImageProvider(R.drawable.ic_kaaba),
                        contentDescription = "Suhoor",
                        modifier = GlanceModifier.size(14.dp)
                    )
                    Spacer(modifier = GlanceModifier.width(4.dp))
                    Text(
                        text = "Suhoor: ${formatTimeRemaining(calculateMinutesUntilTomorrow(state.fajr))}",
                        style = TextStyle(
                            color = ColorProvider(Color(0xFFD4AF37)), // Ramadan Gold
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }

            // Simple footer
            Spacer(modifier = GlanceModifier.height(8.dp))
            Text(
                text = state.hijriDate,
                style = TextStyle(
                    color = ColorProvider(Color(0xFF999999)), // Light gray
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Normal
                )
            )
        }
    }

    private fun formatTimeRemaining(minutes: Int): String {
        return when {
            minutes < 60 -> "${minutes} min remaining"
            minutes == 60 -> "1 hour remaining"
            minutes < 120 -> "1 hour ${minutes % 60} min remaining"
            else -> "${minutes / 60} hours ${minutes % 60} min remaining"
        }
    }

    private fun formatPrayerTime(time: java.time.LocalTime): String {
        return time.toString().substring(0, 5) // HH:MM format
    }

    private fun calculateMinutesUntilTomorrow(time: java.time.LocalTime): Int {
        val now = java.time.LocalTime.now()
        val tomorrow = time.plusHours(24)
        return java.time.Duration.between(now, tomorrow).toMinutes().toInt()
    }

    private fun actionStartActivity() = androidx.glance.action.actionStartActivity<com.hathway.ramadankareem2026.MainActivity>()
}
