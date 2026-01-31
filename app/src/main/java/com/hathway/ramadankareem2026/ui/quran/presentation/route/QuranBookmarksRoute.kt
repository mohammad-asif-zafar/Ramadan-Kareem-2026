package com.hathway.ramadankareem2026.ui.quran.presentation.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.ui.quran.presentation.QuranBookmarksScreen

@Composable
fun QuranBookmarksRoute(
    navController: NavController
) {
    QuranBookmarksScreen(
        onBack = { navController.popBackStack() },
        navController = navController
    )
}
