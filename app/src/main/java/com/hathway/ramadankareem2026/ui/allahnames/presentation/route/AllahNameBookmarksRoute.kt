package com.hathway.ramadankareem2026.ui.allahnames.presentation.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.ui.allahnames.presentation.AllahNameBookmarksScreen

@Composable
fun AllahNameBookmarksRoute(
    navController: NavController
) {
    AllahNameBookmarksScreen(
        onBack = { navController.popBackStack() },
        navController = navController
    )
}
