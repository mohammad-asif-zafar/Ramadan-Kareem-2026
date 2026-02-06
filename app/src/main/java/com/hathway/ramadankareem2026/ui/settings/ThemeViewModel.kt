package com.hathway.ramadankareem2026.ui.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State

class ThemeViewModel : ViewModel() {

    private val _theme = mutableStateOf(AppTheme.SYSTEM)
    val theme: State<AppTheme> = _theme

    fun setTheme(theme: AppTheme) {
        _theme.value = theme
    }
}
