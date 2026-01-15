package com.hathway.ramadankareem2026.ui.widget

import android.content.Context
import android.util.Log
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

class PrayerCountdownWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget =
        PrayerCountdownWidget()

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        Log.d("WIDGET_CHECK", "Prayer widget ENABLED")
        PrayerWidgetScheduler.schedule(context)


    }

}
