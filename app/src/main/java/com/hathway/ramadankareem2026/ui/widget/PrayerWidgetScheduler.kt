package com.hathway.ramadankareem2026.ui.widget

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import java.util.Calendar

object PrayerWidgetScheduler {

    private const val TAG = "PrayerWidgetScheduler"

    fun schedule(context: Context) {
        try {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            
            // Cancel any existing alarms first
            cancel(context, alarmManager)

            val intent = Intent(context, PrayerWidgetUpdateReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context, 
                WIDGET_REQUEST_CODE, 
                intent, 
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            // Calculate next update time
            val nextUpdateTime = calculateNextUpdateTime()
            
            Log.d(TAG, "Scheduling widget update for: ${nextUpdateTime.time}")

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                // Use exact alarm for Android 12+ if permission is granted
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        nextUpdateTime.timeInMillis,
                        pendingIntent
                    )
                } else {
                    // Fallback to inexact alarm
                    alarmManager.setAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        nextUpdateTime.timeInMillis,
                        pendingIntent
                    )
                }
            } else {
                // Use exact alarm for older Android versions
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    nextUpdateTime.timeInMillis,
                    pendingIntent
                )
            }

        } catch (e: SecurityException) {
            Log.e(TAG, "Permission denied for exact alarm scheduling", e)
            // Fallback to inexact alarm
            scheduleInexact(context)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to schedule widget update", e)
            // Schedule a basic fallback
            scheduleFallback(context)
        }
    }

    private fun cancel(context: Context, alarmManager: AlarmManager) {
        try {
            val intent = Intent(context, PrayerWidgetUpdateReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                WIDGET_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            alarmManager.cancel(pendingIntent)
        } catch (e: Exception) {
            Log.w(TAG, "Failed to cancel existing alarm", e)
        }
    }

    private fun scheduleInexact(context: Context) {
        try {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, PrayerWidgetUpdateReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                WIDGET_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.SECOND, 0)
                add(Calendar.MINUTE, 15) // Update every 15 minutes for inexact
            }

            alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        } catch (e: Exception) {
            Log.e(TAG, "Failed to schedule inexact alarm", e)
        }
    }

    private fun scheduleFallback(context: Context) {
        try {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, PrayerWidgetUpdateReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                WIDGET_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.SECOND, 0)
                add(Calendar.MINUTE, 30) // Update every 30 minutes as fallback
            }

            alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        } catch (e: Exception) {
            Log.e(TAG, "Failed to schedule fallback alarm", e)
        }
    }

    private fun calculateNextUpdateTime(): Calendar {
        val now = Calendar.getInstance()
        val nextUpdate = Calendar.getInstance()
        
        // Schedule for the next minute boundary
        nextUpdate.set(
            now.get(Calendar.YEAR),
            now.get(Calendar.MONTH),
            now.get(Calendar.DAY_OF_MONTH),
            now.get(Calendar.HOUR_OF_DAY),
            now.get(Calendar.MINUTE) + 1,
            0
        )
        
        // But don't schedule too far in the future (max 1 hour)
        val maxTime = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            add(Calendar.HOUR, 1)
        }
        
        return if (nextUpdate.after(maxTime)) {
            maxTime
        } else {
            nextUpdate
        }
    }

    fun cancelAll(context: Context) {
        try {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            cancel(context, alarmManager)
            Log.d(TAG, "All widget alarms cancelled")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to cancel all alarms", e)
        }
    }
}

// Constants for the scheduler
private const val WIDGET_REQUEST_CODE = 1001
