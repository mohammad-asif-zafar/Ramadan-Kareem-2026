package com.hathway.ramadankareem2026

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.hathway.ramadankareem2026.ui.navigation.NavGraph
import com.hathway.ramadankareem2026.ui.settings.AppTheme
import com.hathway.ramadankareem2026.ui.settings.ThemeViewModel
import com.hathway.ramadankareem2026.ui.theme.NoorMuslimCompanionTheme

@Composable
fun NoorMuslimCompanionApp(themeViewModel: ThemeViewModel) {
    val currentTheme by themeViewModel.theme
    val isDarkTheme = when (currentTheme) {
        AppTheme.LIGHT -> false
        AppTheme.DARK -> true
        AppTheme.SYSTEM -> isSystemInDarkTheme()
    }
    NoorMuslimCompanionTheme(darkTheme = isDarkTheme) {
        NavGraph(themeViewModel)
    }
}
