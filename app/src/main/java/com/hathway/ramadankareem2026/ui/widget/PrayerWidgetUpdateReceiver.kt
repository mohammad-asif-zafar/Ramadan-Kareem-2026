package com.hathway.ramadankareem2026.ui.widget

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.updateAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PrayerWidgetUpdateReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        CoroutineScope(Dispatchers.IO).launch {

            val manager = GlanceAppWidgetManager(context)
            val widget = PrayerCountdownWidget()
            widget.updateAll(context)
        }
        PrayerWidgetScheduler.schedule(context)

    }
}
