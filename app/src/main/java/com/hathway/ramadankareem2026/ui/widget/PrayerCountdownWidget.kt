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

        // Professional UI/UX design with glassmorphism effect
        Box(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(ColorProvider(Color(0xFF0A0A0A))) // Dark professional background
                .clickable(actionStartActivity())
        ) {
            // Subtle mosque background with professional blur
            Image(
                provider = ImageProvider(R.drawable.mosque_online),
                contentDescription = "Mosque Background",
                modifier = GlanceModifier
                    .fillMaxSize()
                    .padding(12.dp)
            )
            
            // Professional glassmorphism overlay
            Box(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .background(ColorProvider(Color(0x1A1A1A1A))) // Professional glass effect
            ) {}
            
            // Professional content with modern spacing
            Column(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                
                // Professional header with elegant styling
                Row(
                    modifier = GlanceModifier.fillMaxWidth(),
                    horizontalAlignment = androidx.glance.layout.Alignment.Start
                ) {
                    // Location with professional styling
                    Text(
                        text = "${state.city}",
                        style = TextStyle(
                            color = ColorProvider(Color(0xFFFFFFFF)), // Professional white
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Spacer(modifier = GlanceModifier.height(16.dp))

                // Professional prayer section with card-like design
                Column(
                    modifier = GlanceModifier
                        .fillMaxWidth()
                        .background(ColorProvider(Color(0x1AFFFFFF)))
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Current Prayer",
                        style = TextStyle(
                            color = ColorProvider(Color(0xFFB0B0B0)), // Professional gray
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Text(
                        text = "${state.currentPrayer.name}",
                        style = TextStyle(
                            color = ColorProvider(Color(0xFF0F9D58)), // Ramadan Green
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = formatTimeRemaining(state.currentPrayer.minutesRemaining),
                        style = TextStyle(
                            color = ColorProvider(Color(0xFFD4AF37)), // Ramadan Gold
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

                Spacer(modifier = GlanceModifier.height(12.dp))

                // Professional iftar/next prayer section
                Column(
                    modifier = GlanceModifier
                        .fillMaxWidth()
                        .background(ColorProvider(Color(0x1AFFFFFF)))
                        .padding(12.dp)
                ) {
                    if (state.isRamadan) {
                        Text(
                            text = "Iftar Time",
                            style = TextStyle(
                                color = ColorProvider(Color(0xFFB0B0B0)),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                        Text(
                            text = "Maghrib",
                            style = TextStyle(
                                color = ColorProvider(Color(0xFF0F9D58)),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = formatTimeRemaining(state.timeUntilSunset ?: 0),
                            style = TextStyle(
                                color = ColorProvider(Color(0xFFD4AF37)),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    } else {
                        Text(
                            text = "Next Prayer",
                            style = TextStyle(
                                color = ColorProvider(Color(0xFFB0B0B0)),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                        Text(
                            text = "${state.nextPrayer.name}",
                            style = TextStyle(
                                color = ColorProvider(Color(0xFF0F9D58)),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = formatTimeRemaining(state.nextPrayer.minutesRemaining),
                            style = TextStyle(
                                color = ColorProvider(Color(0xFFD4AF37)),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }

                Spacer(modifier = GlanceModifier.height(12.dp))

                // Professional suhoor section (Ramadan only)
                if (state.isRamadan) {
                    Column(
                        modifier = GlanceModifier
                            .fillMaxWidth()
                            .background(ColorProvider(Color(0x1AFFFFFF)))
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "Suhoor Time",
                            style = TextStyle(
                                color = ColorProvider(Color(0xFFB0B0B0)),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                        Text(
                            text = "Fajr",
                            style = TextStyle(
                                color = ColorProvider(Color(0xFF0F9D58)),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = formatTimeRemaining(calculateMinutesUntilTomorrow(state.fajr)),
                            style = TextStyle(
                                color = ColorProvider(Color(0xFFD4AF37)),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }

                Spacer(modifier = GlanceModifier.height(8.dp))

                // Professional footer with elegant styling
                Text(
                    text = state.hijriDate,
                    style = TextStyle(
                        color = ColorProvider(Color(0xFF808080)), // Professional footer gray
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
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
