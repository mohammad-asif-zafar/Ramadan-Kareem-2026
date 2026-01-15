package com.hathway.ramadankareem2026.ui.qibla

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.components.AppTopBar
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.components.SettingToggle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QiblaSettingsScreen(
    navController: NavController,
    viewModel: QiblaViewModel = viewModel(),
    onBack: () -> Unit,
    onViewFullCalendar: () -> Unit,
    onSettings: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val prefs = state.preferences

    Scaffold(
        topBar = {
            RamadanToolbar(
                title = R.string.qibla_settings,
                showBack = true,
                onBackClick = onBack,
                rightIcon1 = Icons.Default.CalendarMonth,
                onRightIcon1Click = onViewFullCalendar,
                rightIcon2 = Icons.Default.Settings,
                onRightIcon2Click = onSettings
            )
        }) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            SettingToggle(
                title = "Vibration on alignment",
                checked = prefs.vibrationEnabled,
                onCheckedChange = {
                    viewModel.updatePreferences(vibrationEnabled = it)
                })

            SettingToggle(
                title = "Show alignment indicator",
                checked = prefs.showAlignmentText,
                onCheckedChange = {
                    viewModel.updatePreferences(showAlignmentText = it)
                })

            SettingToggle(
                title = "Show calibration hint",
                checked = prefs.showCalibrationHint,
                onCheckedChange = {
                    viewModel.updatePreferences(showCalibrationHint = it)
                })
        }
    }
}
