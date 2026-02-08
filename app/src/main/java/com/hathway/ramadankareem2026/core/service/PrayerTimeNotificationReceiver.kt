package com.hathway.ramadankareem2026.core.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.hathway.ramadankareem2026.R
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * BroadcastReceiver for prayer time notifications
 */
class PrayerTimeNotificationReceiver : BroadcastReceiver() {
    
    companion object {
        private const val PRAYER_CHANNEL_ID = "prayer_time_channel"
        private const val PRAYER_NOTIFICATION_ID = 2001
        private const val CHANNEL_NAME = "Prayer Time Notifications"
        private const val CHANNEL_DESCRIPTION = "Notifications for prayer times"
        
        // Prayer names for different languages
        private fun getPrayerName(prayerName: String, language: String): String {
            return when (language.lowercase()) {
                "hi" -> when (prayerName) {
                    "Fajr" -> "फज्र"
                    "Dhuhr" -> "ज़ुहर"
                    "Asr" -> "अस्र"
                    "Maghrib" -> "मग़रिब"
                    "Isha" -> "इशा"
                    else -> prayerName
                }
                "ur" -> when (prayerName) {
                    "Fajr" -> "فجر"
                    "Dhuhr" -> "ظهر"
                    "Asr" -> "عصر"
                    "Maghrib" -> "مغرب"
                    "Isha" -> "عشاء"
                    else -> prayerName
                }
                "ms" -> when (prayerName) {
                    "Fajr" -> "Subuh"
                    "Dhuhr" -> "Zohor"
                    "Asr" -> "Asar"
                    "Maghrib" -> "Maghrib"
                    "Isha" -> "Isyak"
                    else -> prayerName
                }
                else -> prayerName
            }
        }
        
        private fun getPrayerTimeMessage(prayerName: String, language: String): String {
            return when (language.lowercase()) {
                "hi" -> "अब $prayerName का समय हो गया है"
                "ur" -> "اب $prayerName کا وقت ہو گیا ہے"
                "ms" -> "Sekarang masuk waktu $prayerName"
                else -> "It's time for $prayerName prayer"
            }
        }
    }
    
    override fun onReceive(context: Context, intent: Intent) {
        val prayerName = intent.getStringExtra("prayer_name") ?: return
        val prayerTime = intent.getStringExtra("prayer_time") ?: return
        val language = intent.getStringExtra("language") ?: "en"
        
        println("DEBUG: Prayer notification received for $prayerName at $prayerTime")
        
        // Create notification channel for Android 8.0 and above
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                PRAYER_CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = CHANNEL_DESCRIPTION
                enableLights(true)
                enableVibration(true)
                setShowBadge(true)
            }
            notificationManager.createNotificationChannel(channel)
        }
        
        // Create intent to open app when notification is tapped
        val appIntent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            appIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        // Get localized prayer name and message
        val localizedPrayerName = getPrayerName(prayerName, language)
        val message = getPrayerTimeMessage(localizedPrayerName, language)
        
        // Create and show notification
        val notification = NotificationCompat.Builder(context, PRAYER_CHANNEL_ID)
            .setSmallIcon(R.drawable.bell)
            .setContentTitle(localizedPrayerName)
            .setContentText(message)
            .setSubText(prayerTime)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentIntent(pendingIntent)
            .build()
        
        notificationManager.notify(PRAYER_NOTIFICATION_ID, notification)
        
        // Play notification sound
        playPrayerSound(context)
    }
    
    private fun playPrayerSound(context: Context) {
        try {
            // Try to play notification sound
            val notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val ringtone = RingtoneManager.getRingtone(context, notificationUri)
            
            ringtone?.let {
                it.play()
                println("DEBUG: Prayer notification sound played")
            } ?: run {
                println("DEBUG: No notification sound available")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            println("DEBUG: Error playing prayer notification sound: ${e.message}")
        }
    }
}
