package com.hathway.ramadankareem2026.core.localization

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import java.util.*
import androidx.core.content.edit

class LocalizationManager(private val context: Context) {

    companion object {
        private const val PREFS_NAME = "localization_prefs"
        private const val KEY_LANGUAGE = "selected_language"

        // Supported languages
        val SUPPORTED_LANGUAGES = mapOf(
            "en" to Locale.ENGLISH,
            "hi" to Locale("hi", "IN"),
            "ur" to Locale("ur", "PK"),
            "ms" to Locale("ms", "MY")
        )
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getCurrentLanguage(): String {
        return sharedPreferences.getString(KEY_LANGUAGE, "en") ?: "en"
    }

    fun applySavedLanguage() {
        val savedLanguage = getCurrentLanguage()
        val locale = SUPPORTED_LANGUAGES[savedLanguage] ?: Locale.ENGLISH
        
        // Apply locale
        setLocale(locale)
        
        // Handle RTL for Urdu only
        if (savedLanguage == "ur") {
            // Force RTL layout direction
            val config = Configuration(context.resources.configuration)
            config.setLayoutDirection(locale)
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
        }
    }

    fun setLanguage(languageCode: String) {
        val locale = SUPPORTED_LANGUAGES[languageCode] ?: Locale.ENGLISH

        // Save preference
        sharedPreferences.edit { putString(KEY_LANGUAGE, languageCode) }

        // Apply locale
        setLocale(locale)

        // Handle RTL for Urdu only
        if (languageCode == "ur") {
            // Force RTL layout direction
            val config = Configuration()
            config.setLayoutDirection(locale)
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
        }
    }

    private fun setLocale(locale: Locale) {
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

}
