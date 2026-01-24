package com.hathway.ramadankareem2026.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.ui.ramadan.RamadanCalendarScreen
import com.hathway.ramadankareem2026.ui.ramadan.viewmodel.RamadanCalendarViewModel

@Composable
fun RamadanCalendarRoute(
    navController: NavController,
    viewModel: RamadanCalendarViewModel = viewModel()
) {
    val days by viewModel.days.collectAsState()

    RamadanCalendarScreen(
        navController = navController,
        days = days,
        onBack = { navController.popBackStack() },
        onViewFullCalendar = { },
        onSettings = { navController.navigate("settings") }
    )
}