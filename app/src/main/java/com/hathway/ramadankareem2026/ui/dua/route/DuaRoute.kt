package com.hathway.ramadankareem2026.ui.dua.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.ui.dua.DuaScreen
import com.hathway.ramadankareem2026.ui.dua.viewmodel.DuaViewModel

@Composable
fun DuaRoute(routeName: String, navController: NavController) {
    DuaScreen(
        navController = navController,
        onBack = { navController.popBackStack() },
        viewModel = DuaViewModel()
    )
}