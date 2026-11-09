package com.hathway.ramadankareem2026.ui.settings

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.compose.runtime.State

class ThemeViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)

    private val _theme = mutableStateOf(
        try {
            AppTheme.valueOf(prefs.getString("app_theme", AppTheme.SYSTEM.name) ?: AppTheme.SYSTEM.name)
        } catch (_: Exception) {
            AppTheme.SYSTEM
        }
    )
    val theme: State<AppTheme> = _theme

    fun setTheme(theme: AppTheme) {
        _theme.value = theme
        prefs.edit().putString("app_theme", theme.name).apply()
    }
}
