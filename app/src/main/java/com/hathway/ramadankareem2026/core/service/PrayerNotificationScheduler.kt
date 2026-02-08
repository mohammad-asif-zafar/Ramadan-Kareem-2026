package com.hathway.ramadankareem2026.core.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.hathway.ramadankareem2026.core.localization.LocalizationManager
import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeUiState
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Service to schedule prayer time notifications
 */
class PrayerNotificationScheduler {
    
    companion object {
        private const val PRAYER_REQUEST_CODES_START = 3000 // Fajr=3000, Dhuhr=3001, etc.
        
        fun schedulePrayerNotifications(
            context: Context,
            prayerTimes: PrayerTimeUiState,
            isEnabled: Boolean = true
        ) {
            if (!isEnabled) {
                cancelAllPrayerNotifications(context)
                return
            }
            
            val localizationManager = LocalizationManager(context)
            val currentLanguage = localizationManager.getCurrentLanguage()
            
            val prayers = listOf(
                "Fajr" to prayerTimes.fajr,
                "Dhuhr" to prayerTimes.dhuhr,
                "Asr" to prayerTimes.asr,
                "Maghrib" to prayerTimes.maghrib,
                "Isha" to prayerTimes.isha
            )
            
            val today = LocalDate.now()
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            
            prayers.forEachIndexed { index, (prayerName, prayerTime) ->
                val requestCode = PRAYER_REQUEST_CODES_START + index
                val prayerDateTime = LocalDateTime.of(today, prayerTime)
                
                // Only schedule if prayer time is in the future
                if (prayerDateTime.isAfter(LocalDateTime.now())) {
                    scheduleSinglePrayerNotification(
                        context,
                        alarmManager,
                        prayerName,
                        prayerTime,
                        currentLanguage,
                        requestCode,
                        prayerDateTime
                    )
                }
            }
        }
        
        private fun scheduleSinglePrayerNotification(
            context: Context,
            alarmManager: AlarmManager,
            prayerName: String,
            prayerTime: LocalTime,
            language: String,
            requestCode: Int,
            prayerDateTime: LocalDateTime
        ) {
            val intent = Intent(context, PrayerTimeNotificationReceiver::class.java).apply {
                putExtra("prayer_name", prayerName)
                putExtra("prayer_time", prayerTime.format(DateTimeFormatter.ofPattern("hh:mm a")))
                putExtra("language", language)
            }
            
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            
            try {
                // Schedule exact alarm for prayer time
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    prayerDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                    pendingIntent
                )
                
                println("DEBUG: Scheduled $prayerName notification for $prayerDateTime")
            } catch (e: SecurityException) {
                println("DEBUG: No permission to schedule exact alarm: ${e.message}")
                // Fallback to inexact alarm
                alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    prayerDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                    pendingIntent
                )
            } catch (e: Exception) {
                println("DEBUG: Error scheduling prayer notification: ${e.message}")
            }
        }
        
        fun cancelAllPrayerNotifications(context: Context) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            
            // Cancel all prayer notifications
            for (i in 0..4) { // 5 prayers
                val requestCode = PRAYER_REQUEST_CODES_START + i
                val intent = Intent(context, PrayerTimeNotificationReceiver::class.java)
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    requestCode,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
                alarmManager.cancel(pendingIntent)
            }
            
            println("DEBUG: All prayer notifications cancelled")
        }
        
        fun scheduleDailyPrayerNotifications(context: Context) {
            // Schedule to run every day at midnight to refresh prayer notifications
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, PrayerNotificationRefreshReceiver::class.java)
            
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                4000,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            
            // Schedule for midnight (00:00)
            val midnight = LocalDate.now().plusDays(1).atStartOfDay()
            
            val refreshPendingIntent = PendingIntent.getBroadcast(
                context,
                4000,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                midnight.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                AlarmManager.INTERVAL_DAY,
                refreshPendingIntent
            )
            
            println("DEBUG: Daily prayer notification refresh scheduled")
        }
    }
}
