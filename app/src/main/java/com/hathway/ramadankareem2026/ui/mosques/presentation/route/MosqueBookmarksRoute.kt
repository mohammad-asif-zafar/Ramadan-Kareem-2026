package com.hathway.ramadankareem2026.ui.mosques.presentation.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.ui.mosques.presentation.MosqueBookmarksScreen

@Composable
fun MosqueBookmarksRoute(
    navController: NavController
) {
    MosqueBookmarksScreen(
        onBack = { navController.popBackStack() },
        navController = navController
    )
}
