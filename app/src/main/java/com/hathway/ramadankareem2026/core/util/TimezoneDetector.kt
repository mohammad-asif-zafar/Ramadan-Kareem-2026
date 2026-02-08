package com.hathway.ramadankareem2026.core.util

import android.content.Context
import android.location.Location
import java.util.TimeZone

/**
 * Utility class for detecting timezone based on geographic coordinates
 * Uses Android's built-in TimeZone API for accurate detection
 */
object TimezoneDetector {
    
    /**
     * Detect timezone based on latitude and longitude
     * Uses Android's built-in TimeZone API for accurate detection
     */
    fun detectTimezone(context: Context?, latitude: Double, longitude: Double): String {
        return try {
            // Method 1: Try to get timezone from location coordinates
            val locationBasedTimezone = getTimezoneFromCoordinates(latitude, longitude)
            if (locationBasedTimezone != null) {
                return locationBasedTimezone
            }
            
            // Method 2: Use device's default timezone as fallback
            val deviceTimezone = TimeZone.getDefault().id
            android.util.Log.d("TimezoneDetector", "Using device timezone: $deviceTimezone")
            return deviceTimezone
            
        } catch (e: Exception) {
            android.util.Log.e("TimezoneDetector", "Error detecting timezone", e)
            // Ultimate fallback
            return "UTC"
        }
    }
    
    /**
     * Get timezone from coordinates using longitude-based calculation
     */
    private fun getTimezoneFromCoordinates(latitude: Double, longitude: Double): String? {
        return try {
            // Calculate timezone offset from longitude (15 degrees = 1 hour)
            val rawOffset = longitude / 15.0
            
            // Round to nearest hour and convert to milliseconds
            val offsetHours = kotlin.math.round(rawOffset).toInt()
            val offsetMillis = offsetHours * 60 * 60 * 1000L
            
            // Find matching timezone
            val availableTimezones = TimeZone.getAvailableIDs()
            
            // Filter for timezones that match our calculated offset
            val matchingTimezones = availableTimezones.filter { tzId ->
                val tz = TimeZone.getTimeZone(tzId)
                tz.rawOffset.toLong() == offsetMillis
            }
            
            // Prefer common timezones for major regions (using underscores instead of spaces)
            val preferredTimezones = listOf(
                "America/New_York", "America/Los_Angeles", "America/Chicago",
                "Europe/London", "Europe/Paris", "Europe/Berlin",
                "Asia/Kuala_Lumpur", "Asia/Singapore", "Asia/Bangkok",
                "Asia/Jakarta", "Asia/Manila", "Asia/Tokyo",
                "Asia/Dubai", "Asia/Riyadh", "Asia/Kuwait",
                "Asia/Karachi", "Asia/New_Delhi", "Asia/Dhaka",
                "Australia/Sydney", "Australia/Melbourne"
            )
            
            // Return preferred timezone if available, otherwise first match
            preferredTimezones.find { it in matchingTimezones } 
                ?: matchingTimezones.firstOrNull()
                
        } catch (e: Exception) {
            android.util.Log.e("TimezoneDetector", "Error getting timezone from coordinates", e)
            null
        }
    }
    
    /**
     * Get timezone name for display
     */
    fun getTimezoneDisplayName(timezoneId: String): String {
        return try {
            val timeZone = TimeZone.getTimeZone(timezoneId)
            timeZone.displayName
        } catch (e: Exception) {
            timezoneId
        }
    }
    
    /**
     * Check if timezone ID is valid
     */
    fun isValidTimezone(timezoneId: String): Boolean {
        return try {
            TimeZone.getTimeZone(timezoneId)
            true
        } catch (e: Exception) {
            false
        }
    }
}
