package com.hathway.ramadankareem2026.ui.widget

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.updateAll
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class PrayerWidgetUpdateReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "PrayerWidgetUpdate"
    }

    private val widgetScope = CoroutineScope(
        SupervisorJob() + Dispatchers.IO + 
        CoroutineExceptionHandler { _, throwable ->
            Log.e(TAG, "Widget update failed", throwable)
        }
    )

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "Received widget update request")

        widgetScope.launch {
            try {
                // Check if widget is still enabled before updating
                val manager = GlanceAppWidgetManager(context)
                val widgetIds = manager.getGlanceIds(PrayerCountdownWidget::class.java)
                
                if (widgetIds.isEmpty()) {
                    Log.d(TAG, "No active widgets found, skipping update")
                    return@launch
                }

                Log.d(TAG, "Updating ${widgetIds.size} widget(s)")
                
                // Update all widgets
                val widget = PrayerCountdownWidget()
                widget.updateAll(context)
                
                Log.d(TAG, "Widget update completed successfully")
                
            } catch (e: Exception) {
                Log.e(TAG, "Failed to update widget", e)
            } finally {
                // Schedule next update regardless of success/failure
                try {
                    PrayerWidgetScheduler.schedule(context)
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to schedule next update", e)
                }
            }
        }
    }
}
