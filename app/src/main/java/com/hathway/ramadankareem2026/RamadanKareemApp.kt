package com.hathway.ramadankareem2026

import androidx.compose.runtime.Composable
import com.hathway.ramadankareem2026.ui.navigation.NavGraph
import com.hathway.ramadankareem2026.ui.settings.ThemeViewModel
import com.hathway.ramadankareem2026.ui.theme.NoorMuslimCompanionTheme

@Composable
fun NoorMuslimCompanionApp(themeViewModel: ThemeViewModel) {
    NoorMuslimCompanionTheme {
        NavGraph(themeViewModel)
    }
}


