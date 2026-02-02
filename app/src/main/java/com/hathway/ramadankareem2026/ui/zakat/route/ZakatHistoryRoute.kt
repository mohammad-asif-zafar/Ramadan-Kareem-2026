package com.hathway.ramadankareem2026.ui.zakat.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import android.widget.Toast
import com.hathway.ramadankareem2026.data.local.database.RamadanDatabase
import com.hathway.ramadankareem2026.data.repository.ZakatCalculationRepository
import com.hathway.ramadankareem2026.ui.zakat.presentation.ZakatHistoryScreen
import com.hathway.ramadankareem2026.ui.zakat.viewmodel.ZakatCalculationViewModel

@Composable
fun ZakatHistoryRoute(navController: NavController) {
    // Create repository and ViewModel
    val context = LocalContext.current
    val database = RamadanDatabase.getDatabase(context)
    val repository = ZakatCalculationRepository(database.zakatCalculationDao())
    val viewModel: ZakatCalculationViewModel = viewModel(
        factory = object : androidx.lifecycle.ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return ZakatCalculationViewModel(repository) as T
            }
        }
    )

    val calculations by viewModel.calculations.collectAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.collectAsState(initial = false)
    val errorMessage by viewModel.errorMessage.collectAsState(initial = null)



    // Show toast if calculations are empty
    LaunchedEffect(calculations) {
        if (calculations.isEmpty()) {
            Toast.makeText(
                context,
                "No previously zakat calculation found",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    ZakatHistoryScreen(
        calculations = calculations,
        onDeleteCalculation = { calculation ->
            viewModel.deleteCalculation(calculation)
        },
        onDeleteAll = {
            viewModel.deleteAllCalculations()
        },
        onBack = {
            navController.popBackStack()
        }
    )
}
