package com.hathway.ramadankareem2026.ui.dua.presentation.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.ui.dua.presentation.DuaBookmarksScreen

@Composable
fun DuaBookmarksRoute(
    navController: NavController
) {
    DuaBookmarksScreen(
        onBack = { navController.popBackStack() },
        navController = navController
    )
}
