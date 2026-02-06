package com.hathway.ramadankareem2026.ui.home.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat

/**
 * BroadcastReceiver to handle alarm triggers from AlarmManager
 */
class AlarmReceiver : BroadcastReceiver() {
    
    companion object {
        private const val ALARM_CHANNEL_ID = "alarm_channel"
        private const val ALARM_NOTIFICATION_ID = 1001
        private const val CHANNEL_NAME = "Alarm Notifications"
        private const val CHANNEL_DESCRIPTION = "Notifications for alarm sounds"
        private const val ACTION_STOP_ALARM = "com.hathway.ramadankareem2026.STOP_ALARM"
    }
    
    override fun onReceive(context: Context, intent: Intent) {
        // Handle stop alarm action
        if (intent.action == ACTION_STOP_ALARM) {
            // Cancel notification
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(ALARM_NOTIFICATION_ID)
            
            // Stop any playing ringtone
            try {
                // Stop system ringtone
                val ringtoneManager = RingtoneManager.getRingtone(context, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
                ringtoneManager?.stop()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            
            println("DEBUG: Alarm stopped by user")
            return
        }
        
        val alarmType = intent.getStringExtra("alarm_type") ?: "Unknown"
        
        println("DEBUG: Alarm received for $alarmType")
        
        // Create notification channel for Android 8.0 and above
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                ALARM_CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = CHANNEL_DESCRIPTION
                enableLights(true)
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
        }
        
        // Create intent for Stop Alarm action
        val stopAlarmIntent = Intent(context, AlarmReceiver::class.java).apply {
            action = ACTION_STOP_ALARM
        }
        val stopAlarmPendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            stopAlarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        // Create and show notification with Stop Alarm action
        val notification = NotificationCompat.Builder(context, ALARM_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle("Ramadan Alarm")
            .setContentText("$alarmType alarm has started")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(false) // Don't auto-cancel for alarm notifications
            .addAction(
                android.R.drawable.ic_delete,
                "Stop Alarm",
                stopAlarmPendingIntent
            )
            .build()
        
        notificationManager.notify(ALARM_NOTIFICATION_ID, notification)
        
        // Play alarm sound
        playAlarmSound(context)
    }
    
    private fun playAlarmSound(context: Context) {
        try {
            // Try multiple approaches to play alarm sound
            var ringtone: android.media.Ringtone? = null
            
            // Method 1: Try default alarm ringtone
            val alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            ringtone = RingtoneManager.getRingtone(context, alarmUri)
            
            // Method 2: If no alarm ringtone, try notification sound
            if (ringtone == null) {
                val notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                ringtone = RingtoneManager.getRingtone(context, notificationUri)
            }
            
            // Method 3: If still no ringtone, try ringtone
            if (ringtone == null) {
                val ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
                ringtone = RingtoneManager.getRingtone(context, ringtoneUri)
            }
            
            // Play ringtone if found
            ringtone?.let {
                it.play()
                println("DEBUG: Alarm sound started")
            } ?: run {
                println("DEBUG: No ringtone available")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            println("DEBUG: Error playing alarm sound: ${e.message}")
        }
    }
}
