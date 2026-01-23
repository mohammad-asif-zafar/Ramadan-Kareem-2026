package com.hathway.ramadankareem2026.ui.zakat.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.ui.navigation.Routes
import com.hathway.ramadankareem2026.ui.zakat.presentation.ZakatBreakdownScreen
import com.hathway.ramadankareem2026.ui.zakat.presentation.viewmodel.ZakatViewModel


@Composable
fun ZakatBreakdownRoute(navController: NavController) {

    val parentEntry = remember(navController) {
        navController.getBackStackEntry(Routes.ZAKAT)
    }

    val viewModel: ZakatViewModel = viewModel(parentEntry)
    val result = viewModel.uiState.collectAsState().value.result ?: return

    ZakatBreakdownScreen(
        result = result,
        onBack = { navController.popBackStack() }
    )
}
