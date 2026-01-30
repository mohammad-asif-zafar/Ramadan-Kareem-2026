package com.hathway.ramadankareem2026.ui.allahnames

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.ui.allahnames.viewmodel.AllahNamesViewModel

@Composable
fun AllahNameDetailRoute(
    id: Int, navController: NavController, viewModel: AllahNamesViewModel = viewModel()
) {
    //  Safe lookup (no crash)
    val name = viewModel.names.firstOrNull { it.id == id } ?: return

    //  Route only wires data + navigation
    AllahNameDetailScreen(
        name = name, onBack = { navController.popBackStack() })
}
