package com.hathway.ramadankareem2026.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.LocalContext
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.*
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.unit.ColorProvider
import androidx.glance.text.TextStyle

class PrayerCountdownWidget : GlanceAppWidget() {

    override suspend fun provideGlance(
        context: Context,
        id: GlanceId
    ) {
        provideContent {
            WidgetContent()
        }
    }

    @SuppressLint("RestrictedApi", "ComposableNaming")

    @Composable
    private  fun WidgetContent() {

        val context = LocalContext.current
        val state = PrayerWidgetDataProvider.load(context)

        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .padding(12.dp)
                .background(ColorProvider(Color(0xFF1B5E20)))
        ) {

            Text(
                text = "Next Prayer",
                style = TextStyle(
                    color = ColorProvider(Color.White),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            )

            Spacer(modifier = GlanceModifier.height(6.dp))

            Text(
                text = "${state.prayerName} in ${state.minutes} min",
                style = TextStyle(
                    color = ColorProvider(Color(0xFFFFD54F)),
                    fontWeight = FontWeight.Bold,
                    fontSize =18.sp
                )
            )

            Spacer(modifier = GlanceModifier.height(4.dp))

            Text(
                text = state.city,
                style = TextStyle(
                    color = ColorProvider(Color.White),
                    fontSize = 12.sp
                )
            )
        }
    }
}
