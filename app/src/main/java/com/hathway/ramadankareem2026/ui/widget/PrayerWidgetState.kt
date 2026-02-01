package com.hathway.ramadankareem2026.ui.widget

import java.time.LocalDate
import java.time.LocalTime

data class PrayerWidgetState(
    // 🕌 All prayer times from app
    val fajr: LocalTime,
    val sunrise: LocalTime,
    val dhuhr: LocalTime,
    val asr: LocalTime,
    val maghrib: LocalTime,
    val isha: LocalTime,
    
    // 📍 Location information
    val city: String,
    val country: String,
    
    // ⏰ Current and next prayer info
    val currentPrayer: PrayerInfo,
    val nextPrayer: PrayerInfo,
    
    // 📅 Date information
    val gregorianDate: LocalDate,
    val hijriDate: String,
    
    // 🌙 Ramadan and fasting info
    val isRamadan: Boolean,
    val fastingStatus: FastingStatus,
    val ramadanDay: Int? = null,
    
    // 🌤️ Additional context
    val currentTemperature: String? = null,
    val weatherCondition: String? = null,
    val qiblaDirection: String? = null,
    
    // ⏱️ Timing context
    val timeUntilSunrise: Int? = null,
    val timeUntilSunset: Int? = null,
    val dayProgress: Float = 0f,
    
    // 🔄 Update tracking
    val lastUpdated: Long = System.currentTimeMillis()
)

data class PrayerInfo(
    val name: String,
    val arabicName: String,
    val time: LocalTime,
    val minutesRemaining: Int,
    val urgency: PrayerUrgency = PrayerUrgency.NORMAL,
    val isPassed: Boolean = false
)

enum class PrayerUrgency {
    URGENT,      // Less than 15 minutes
    SOON,        // Less than 30 minutes  
    NORMAL       // More than 30 minutes
}

enum class FastingStatus {
    FASTING,     // Currently fasting
    IFTAR_TIME,  // Time to break fast
    SUHOOR_TIME, // Time for pre-dawn meal
    NOT_RAMADAN  // Not Ramadan period
}
