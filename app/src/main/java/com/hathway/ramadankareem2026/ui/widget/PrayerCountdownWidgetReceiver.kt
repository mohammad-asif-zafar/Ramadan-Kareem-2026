package com.hathway.ramadankareem2026.ui.widget

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

class PrayerCountdownWidgetReceiver : GlanceAppWidgetReceiver() {
    
    companion object {
        private const val TAG = "PrayerWidgetReceiver"
    }

    override val glanceAppWidget: GlanceAppWidget = PrayerCountdownWidget()

    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        Log.d(TAG, "Prayer widget ENABLED")
        
        try {
            // Schedule initial widget updates
            PrayerWidgetScheduler.schedule(context)
            Log.d(TAG, "Widget updates scheduled successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to schedule widget updates", e)
        }
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        Log.d(TAG, "Prayer widget DISABLED")
        
        try {
            // Cancel all widget updates when widget is disabled
            PrayerWidgetScheduler.cancelAll(context)
            Log.d(TAG, "Widget updates cancelled")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to cancel widget updates", e)
        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        super.onDeleted(context, appWidgetIds)
        Log.d(TAG, "Prayer widget DELETED for IDs: ${appWidgetIds.contentToString()}")
        
        try {
            // Clean up any widget-specific data if needed
            PrayerWidgetScheduler.cancelAll(context)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to clean up widget data", e)
        }
    }

    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    override fun onRestored(context: Context, oldWidgetIds: IntArray, newWidgetIds: IntArray) {
        super.onRestored(context, oldWidgetIds, newWidgetIds)
        Log.d(TAG, "Prayer widget RESTORED from ${oldWidgetIds.contentToString()} to ${newWidgetIds.contentToString()}")
        
        try {
            // Reschedule updates when widget is restored
            PrayerWidgetScheduler.schedule(context)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to reschedule widget updates after restore", e)
        }
    }
}
