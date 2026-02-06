package com.hathway.ramadankareem2026

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.ramadankareem2026.core.localization.LocalizationManager
import com.hathway.ramadankareem2026.ui.settings.AppTheme
import com.hathway.ramadankareem2026.ui.settings.ThemeViewModel
import com.hathway.ramadankareem2026.ui.theme.RamadanKareemTheme

class MainActivity : ComponentActivity() {
    private lateinit var localizationManager: LocalizationManager

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        // ✅ REQUIRED for Android 12+ splash screen support
        installSplashScreen()

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        localizationManager = LocalizationManager(this)
        localizationManager.applySavedLanguage()

        setContent {
            val themeViewModel: ThemeViewModel = viewModel()
            val currentTheme by themeViewModel.theme

            val isDarkTheme = when (currentTheme) {
                AppTheme.LIGHT -> false
                AppTheme.DARK -> true
                AppTheme.SYSTEM -> isSystemInDarkTheme()
            }

            RamadanKareemTheme(darkTheme = isDarkTheme) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RamadanKareemApp(themeViewModel)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val themeViewModel: ThemeViewModel = viewModel()

    RamadanKareemTheme {
        RamadanKareemApp(
            themeViewModel = themeViewModel
        )
    }
}