package com.hathway.ramadankareem2026.ui.zakat.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import android.widget.Toast
import com.hathway.ramadankareem2026.core.currency.LocationCurrencyService
import com.hathway.ramadankareem2026.data.local.database.RamadanDatabase
import com.hathway.ramadankareem2026.data.repository.ZakatCalculationRepository
import com.hathway.ramadankareem2026.ui.navigation.Routes
import com.hathway.ramadankareem2026.ui.zakat.presentation.ZakatScreen
import com.hathway.ramadankareem2026.ui.zakat.presentation.viewmodel.ZakatViewModel
import com.hathway.ramadankareem2026.ui.zakat.viewmodel.ZakatCalculationViewModel

@Composable
fun ZakatRoute(navController: NavController) {

    val context = LocalContext.current

    // Create currency service
    val currencyService = remember { LocationCurrencyService(context) }

    // Create ZakatViewModel with currency service
    val viewModel: ZakatViewModel = viewModel(
        factory = object : androidx.lifecycle.ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return ZakatViewModel(currencyService) as T
            }
        }
    )

    val state by viewModel.uiState.collectAsState()

    // Create calculation repository and ViewModel
    val database = remember { RamadanDatabase.getDatabase(context) }
    val repository = remember { ZakatCalculationRepository(database.zakatCalculationDao()) }
    val calculationViewModel: ZakatCalculationViewModel = viewModel(
        factory = object : androidx.lifecycle.ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return ZakatCalculationViewModel(repository) as T
            }
        }
    )
    
    val zakatHistoryCount by calculationViewModel.calculationsCount.collectAsState(initial = 0)

    // Auto-save calculation when result is available
    LaunchedEffect(state.result) {
        state.result?.let { result ->
            println("DEBUG: Auto-saving calculation with result: $result")
            calculationViewModel.saveCalculation(
                goldValue = result.assets.gold,
                silverValue = result.assets.silver,
                cash = result.assets.cash,
                debts = result.assets.debts,
                nisabType = result.nisabType.name.lowercase(),
                totalAssets = result.assets.gold + result.assets.silver + result.assets.cash,
                totalLiabilities = result.assets.debts,
                zakatPayable = result.zakatAmount
            )
            println("DEBUG: Auto-save calculation completed")
        }
    }

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
        onBack = { navController.popBackStack() },
        navController = navController,
        zakatHistoryCount = zakatHistoryCount,
        onCalculatorIconClick = {
            if (zakatHistoryCount > 0) {
                navController.navigate(Routes.ZAKAT_HISTORY)
            } else {
                Toast.makeText(
                    context,
                    "No zakat history available",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    )
}


