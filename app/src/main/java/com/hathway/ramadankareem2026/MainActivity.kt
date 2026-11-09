package com.hathway.ramadankareem2026

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import com.hathway.ramadankareem2026.ui.navigation.NavViewModel
import com.hathway.ramadankareem2026.ui.settings.AppTheme
import com.hathway.ramadankareem2026.ui.settings.ThemeViewModel
import com.hathway.ramadankareem2026.ui.theme.NoorMuslimCompanionTheme

class MainActivity : ComponentActivity() {
    private val navViewModel: NavViewModel by viewModels()
    private lateinit var localizationManager: LocalizationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        // Dismiss the native system splash canvas immediately so Compose animations kick in instantly
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { false }

        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

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

            NoorMuslimCompanionTheme(darkTheme = isDarkTheme) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NoorMuslimCompanionApp(themeViewModel)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val themeViewModel: ThemeViewModel = viewModel()

    NoorMuslimCompanionTheme {
        NoorMuslimCompanionApp(
            themeViewModel = themeViewModel
        )
    }
}
