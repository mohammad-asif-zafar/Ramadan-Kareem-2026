package com.hathway.ramadankareem2026.ui.mosques.presentation.route

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.core.location.LocationRepository
import com.hathway.ramadankareem2026.ui.mosques.NearbyMosquesScreen
import com.hathway.ramadankareem2026.ui.mosques.data.remote.PlacesApiProvider
import com.hathway.ramadankareem2026.ui.mosques.data.repository.MosqueRepositoryImpl
import com.hathway.ramadankareem2026.ui.mosques.domain.usecase.GetNearbyMosquesUseCase
import com.hathway.ramadankareem2026.ui.mosques.domain.usecase.GetUserLocationForMosquesUseCase
import com.hathway.ramadankareem2026.ui.mosques.presentation.viewmodel.NearbyMosquesViewModel
import com.hathway.ramadankareem2026.ui.mosques.presentation.viewmodel.NearbyMosquesViewModelFactory

@Composable
fun MosqueRoute(
    navController: NavController
) {
    val context = LocalContext.current

    // --- Core dependency (already exists in your app)
    val locationRepository = remember {
        LocationRepository(context)
    }

    // --- Mosque feature dependencies
    val mosqueRepository = remember {
        MosqueRepositoryImpl(
            api = PlacesApiProvider.create() // retrofit provider
        )
    }

    val viewModel: NearbyMosquesViewModel = viewModel(
        factory = NearbyMosquesViewModelFactory(
            getLocation = GetUserLocationForMosquesUseCase(locationRepository),
            getNearbyMosques = GetNearbyMosquesUseCase(mosqueRepository)
        )
    )

    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.load()
    }

    NearbyMosquesScreen(
        state = state,
        onBack = { navController.popBackStack() },
        onMosqueClick = viewModel::selectMosque
    )
}

