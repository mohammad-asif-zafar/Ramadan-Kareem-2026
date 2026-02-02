package com.hathway.ramadankareem2026.ui.dua.route

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.ui.dua.DuaScreen

@Composable
fun DuaRoute(routeName: String, navController: NavController) {
    DuaScreen(
        navController = navController,
        onBack = { navController.popBackStack() },
        viewModel = viewModel(),
        bookmarkViewModel = viewModel(),
        duaBookmarkCountViewModel = viewModel()
    )
}