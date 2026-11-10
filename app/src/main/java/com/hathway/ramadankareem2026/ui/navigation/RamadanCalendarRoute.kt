package com.hathway.ramadankareem2026.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.ui.ramadan.RamadanCalendarScreen
import com.hathway.ramadankareem2026.ui.ramadan.viewmodel.RamadanCalendarViewModel
import com.hathway.ramadankareem2026.ui.ramadan.viewmodel.RamadanCalendarViewModelFactory

@Composable
fun RamadanCalendarRoute(
    navController: NavController,
    viewModel: RamadanCalendarViewModel = viewModel(
        factory = RamadanCalendarViewModelFactory(LocalContext.current.applicationContext as android.app.Application)
    )
) {
    val days by viewModel.days.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val isHijriMode by viewModel.isHijriMode.collectAsState()
    val monthTitle by viewModel.currentMonthYearText.collectAsState()
    val monthSubtitle by viewModel.monthSubtitleText.collectAsState()

    RamadanCalendarScreen(
        navController = navController,
        days = days,
        isLoading = isLoading,
        error = error,
        isHijriMode = isHijriMode,
        monthTitle = monthTitle,
        monthSubtitle = monthSubtitle,
        onTabSelected = { isHijri -> viewModel.setCalendarMode(isHijri) },
        onNextMonth = { viewModel.nextMonth() },
        onPrevMonth = { viewModel.previousMonth() },
        onBack = { navController.popBackStack() },
        onViewFullCalendar = { },
        onSettings = { navController.navigate(Routes.SETTINGS) },
        onRefresh = { viewModel.refresh() },
        onLocationUpdate = { lat, lng ->
            viewModel.updateLocation(lat, lng)
        },
        onClearError = { viewModel.clearError() }
    )
}
