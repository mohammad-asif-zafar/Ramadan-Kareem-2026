package com.hathway.ramadankareem2026.ui.home.viewmodel

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.media.ToneGenerator
import android.os.Build
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * ViewModel for managing alarm states and sound playback
 */
class AlarmViewModel : ViewModel() {
    
    companion object {
        private const val ALARM_CHANNEL_ID = "alarm_channel"
        private const val ALARM_NOTIFICATION_ID = 1001
        private const val CHANNEL_NAME = "Alarm Notifications"
        private const val CHANNEL_DESCRIPTION = "Notifications for alarm sounds"
    }
    
    // Alarm states
    var isIftarAlarmEnabled = mutableStateOf(false)
        private set
    
    var isSuhoorAlarmEnabled = mutableStateOf(false)
        private set
    
    // State to trigger recomposition
    var alarmTrigger = mutableStateOf(0)
        private set
    
    // Ringtone reference to persist across slider movements
    private var currentRingtone: android.media.Ringtone? = null
    
    // Job tracking to cancel pending alarms
    private var iftarAlarmJob: kotlinx.coroutines.Job? = null
    private var suhoorAlarmJob: kotlinx.coroutines.Job? = null
    
    init {
        // Initialize with default values
        resetAlarmStates()
    }
    
    /**
     * Request notification permission if not granted
     */
    private fun requestNotificationPermission(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != 
                android.content.pm.PackageManager.PERMISSION_GRANTED) {
                // Request permission - this would typically be called from an Activity
                // For now, we'll log and skip notification
                println("DEBUG: POST_NOTIFICATIONS permission not granted - user needs to enable in settings")
            }
        }
    }
    
    /**
     * Show notification when alarm starts
     */
    private fun showAlarmNotification(context: Context, alarmType: String) {
        try {
            // Check notification permission for Android 13+
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != 
                    android.content.pm.PackageManager.PERMISSION_GRANTED) {
                    println("DEBUG: POST_NOTIFICATIONS permission not granted - requesting permission")
                    requestNotificationPermission(context)
                    return
                }
            }
            
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            
            // Create notification channel for Android 8.0 and above
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
            
            // Create notification
            val notification = NotificationCompat.Builder(context, ALARM_CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                .setContentTitle("Ramadan Alarm")
                .setContentText("$alarmType alarm has started")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .build()
            
            // Show notification
            notificationManager.notify(ALARM_NOTIFICATION_ID, notification)
            println("DEBUG: Alarm notification shown for $alarmType")
        } catch (e: Exception) {
            e.printStackTrace()
            println("DEBUG: Error showing notification: ${e.message}")
        }
    }
    
    /**
     * Initialize alarm states from SharedPreferences
     */
    fun initializeAlarmStates(context: Context) {
        isIftarAlarmEnabled.value = isIftarAlarmEnabled(context)
        isSuhoorAlarmEnabled.value = isSuhoorAlarmEnabled(context)
    }
    
    /**
     * Reset alarm states to default values
     */
    private fun resetAlarmStates() {
        isIftarAlarmEnabled.value = false
        isSuhoorAlarmEnabled.value = false
        alarmTrigger.value = 0
    }
    
    /**
     * Toggle Iftar alarm state
     */
    fun toggleIftarAlarm(context: Context) {
        val currentState = isIftarAlarmEnabled.value
        val newState = !currentState
        
        // Update SharedPreferences
        getAlarmPreferences(context).edit { 
            putBoolean("iftar_alarm_enabled", newState) 
        }
        
        // Update local state
        isIftarAlarmEnabled.value = newState
        
        // Trigger recomposition
        alarmTrigger.value++
        
        // Debug log
        println("DEBUG: Iftar alarm toggled from $currentState to $newState")
        
        // Cancel any pending alarm if turning OFF
        if (!newState) {
            iftarAlarmJob?.cancel()
            iftarAlarmJob = null
        }
        
        // Play sounds if enabling alarm
        if (newState) {
            // Show notification when alarm starts
            showAlarmNotification(context, "Iftar")
            
            playTestSound(context)
            
            // Schedule default alarm after 20 seconds with job tracking
            iftarAlarmJob = viewModelScope.launch {
                delay(20000)
                playDefaultAlarm(context)
            }
        }
    }
    
    /**
     * Toggle Suhoor alarm state
     */
    fun toggleSuhoorAlarm(context: Context) {
        val currentState = isSuhoorAlarmEnabled.value
        val newState = !currentState
        
        // Update SharedPreferences
        getAlarmPreferences(context).edit { 
            putBoolean("suhoor_alarm_enabled", newState) 
        }
        
        // Update local state
        isSuhoorAlarmEnabled.value = newState
        
        // Trigger recomposition
        alarmTrigger.value++
        
        // Debug log
        println("DEBUG: Suhoor alarm toggled from $currentState to $newState")
        
        // Cancel any pending alarm if turning OFF
        if (!newState) {
            suhoorAlarmJob?.cancel()
            suhoorAlarmJob = null
        }
        
        // Play sounds if enabling alarm
        if (newState) {
            // Show notification when alarm starts
            showAlarmNotification(context, "Suhoor")
            
            playTestSound(context)
            
            // Schedule default alarm after 20 seconds with job tracking
            suhoorAlarmJob = viewModelScope.launch {
                delay(20000)
                playDefaultAlarm(context)
            }
        }
    }
    
    /**
     * Get SharedPreferences for alarm settings
     */
    private fun getAlarmPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("alarm_prefs", Context.MODE_PRIVATE)
    }
    
    /**
     * Check if Iftar alarm is enabled
     */
    private fun isIftarAlarmEnabled(context: Context): Boolean {
        return getAlarmPreferences(context).getBoolean("iftar_alarm_enabled", false)
    }
    
    /**
     * Check if Suhoor alarm is enabled
     */
    private fun isSuhoorAlarmEnabled(context: Context): Boolean {
        return getAlarmPreferences(context).getBoolean("suhoor_alarm_enabled", false)
    }
    
    /**
     * Play a simple test sound
     */
    private fun playTestSound(context: Context) {
        try {
            // Play a simple beep immediately to test sound system
            val toneGenerator = ToneGenerator(android.media.AudioManager.STREAM_MUSIC, 100)
            toneGenerator.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 200)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    /**
     * Cancel alarm notification
     */
    private fun cancelAlarmNotification(context: Context) {
        try {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(ALARM_NOTIFICATION_ID)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    /**
     * Stop alarm manually (user action)
     */
    fun stopAlarm(context: Context) {
        // Cancel notification when alarm stops
        cancelAlarmNotification(context)
        
        currentRingtone?.let {
            it.stop()
            currentRingtone = null
        }
    }
    
    /**
     * Play default alarm sound
     */
    private fun playDefaultAlarm(context: Context) {
        try {
            // Check permissions only for older devices (Android 10 and below)
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
                if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != 
                    android.content.pm.PackageManager.PERMISSION_GRANTED) {
                    // Permission not granted, use fallback sound
                    playTestSound(context)
                    return
                }
            }

            // Stop any existing ringtone before playing new one
            currentRingtone?.let {
                it.stop()
                currentRingtone = null
            }

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
            
            // Play ringtone if found and store reference
            ringtone?.let {
                currentRingtone = it
                it.play()
                // Auto-stop after 5 seconds to avoid infinite playing
                viewModelScope.launch {
                    delay(5000)
                    currentRingtone?.let { ringtone ->
                        ringtone.stop()
                        currentRingtone = null
                        // Cancel notification when auto-stopped
                        cancelAlarmNotification(context)
                    }
                }
            } ?: run {
                // Fallback: Use system beep if no ringtone available
                playTestSound(context)
            }
        } catch (e: Exception) {
            // Handle error silently for testing
            e.printStackTrace()
            // Fallback to test sound
            playTestSound(context)
        }
    }
    
    /**
     * Cleanup when ViewModel is destroyed
     */
    override fun onCleared() {
        super.onCleared()
        
        // Cancel all pending alarm jobs
        iftarAlarmJob?.cancel()
        suhoorAlarmJob?.cancel()
        
        // Stop any playing ringtone to prevent memory leaks
        currentRingtone?.let {
            it.stop()
            currentRingtone = null
        }
    }
}
