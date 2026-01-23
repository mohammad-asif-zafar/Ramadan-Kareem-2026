package com.hathway.ramadankareem2026.ui.zakat.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.ui.navigation.Routes
import com.hathway.ramadankareem2026.ui.zakat.presentation.ZakatScreen
import com.hathway.ramadankareem2026.ui.zakat.presentation.viewmodel.ZakatViewModel

@Composable
fun ZakatRoute(navController: NavController) {

    val parentEntry = remember(navController) {
        navController.getBackStackEntry(Routes.ZAKAT)
    }

    val viewModel: ZakatViewModel = viewModel(parentEntry)
    val state = viewModel.uiState.collectAsState().value

    ZakatScreen(
        state = state,
        onGold = viewModel::onGold,
        onSilver = viewModel::onSilver,
        onCash = viewModel::onCash,
        onDebts = viewModel::onDebts,
        onNisab = viewModel::onNisab,
        onCalculate = viewModel::calculate,
        onViewBreakdown = {
            if (state.result != null) {
                navController.navigate(Routes.ZAKAT_BREAKDOWN)
            }
        },
        onBack = { navController.popBackStack() }
    )
}


