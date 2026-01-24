package com.hathway.ramadankareem2026.ui.mosques.presentation.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.ui.mosques.MosqueMapWithList
import com.hathway.ramadankareem2026.ui.mosques.presentation.viewmodel.MosqueViewModel

@Composable
fun MosqueRoute(
    navController: NavController,
    viewModel: MosqueViewModel = viewModel(),
) {
    val mosques by viewModel.mosques.collectAsState()

    // TODO: replace with real location
    LaunchedEffect(Unit) {
        viewModel.loadNearby(
            lat = 3.1390,  // Kuala Lumpur
            lng = 101.6869
        )
    }

   /* MosqueMapWithList(
        mosques = mosques
    )*/
}
