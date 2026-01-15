package com.hathway.ramadankareem2026.ui.dua

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.navigation.Routes

@Composable
fun DuaScreen(
    titleScreenName: String,
    navController: NavController,
    onBack: () -> Unit,
    onViewFullCalendar: () -> Unit,
    onSettings: () -> Unit
) {
    BackHandler {
        navController.popBackStack()
    }

    Scaffold(
        topBar = {
            RamadanToolbar(
                title = R.string.feature_dua,
                showBack = true,
                onBackClick = onBack,
                rightIcon1 = Icons.Default.CalendarMonth,
                onRightIcon1Click = onViewFullCalendar,
                rightIcon2 = Icons.Default.Settings,
                onRightIcon2Click = { navController.navigate(Routes.QIBLA_SETTINGS) }
            )
        }) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding), contentAlignment = Alignment.Center
        ) {
            Text("Dua Screen Content")
        }
    }
}
