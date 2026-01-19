package com.hathway.ramadankareem2026.ui.home

import android.Manifest
import android.app.Application
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

// 🔹 Core location models
import com.hathway.ramadankareem2026.core.location.LocationUiState

import com.hathway.ramadankareem2026.core.location.LocationSource
import com.hathway.ramadankareem2026.core.location.LocationSelectionMode

// 🔹 UI components
import com.hathway.ramadankareem2026.ui.home.components.FeatureSection
import com.hathway.ramadankareem2026.ui.home.components.HomeHeaderSlider
import com.hathway.ramadankareem2026.ui.home.components.PrayerTimeSection
import com.hathway.ramadankareem2026.ui.home.components.TodayTipSection
import com.hathway.ramadankareem2026.ui.home.components.TopBarSection

// 🔹 ViewModels
import com.hathway.ramadankareem2026.ui.home.homeViewModel.HomeViewModel
import com.hathway.ramadankareem2026.ui.prayer.PrayerViewModel

// 🔹 Preview only
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.hathway.ramadankareem2026.ui.prayer.data.PrayerViewModelFactory

private const val TAG = "HomeScreen"

@Composable
fun HomeScreen(
    navController: NavController
) {
    val homeViewModel: HomeViewModel = viewModel()

    val context = LocalContext.current
    val app = context.applicationContext as Application

    val prayerViewModel: PrayerViewModel = viewModel(
        factory = PrayerViewModelFactory(app)
    )


    // Sealed UI state
    val locationState by homeViewModel.locationState.collectAsState()

    Log.e(TAG, "HomeScreen1: $locationState")

    // Permission launcher (upgrade path)
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            homeViewModel.loadLocation()
            Log.e(TAG, "HomeScreen:2 $locationState")

        }
    }

    // Initial load (DEMO → REAL)
    LaunchedEffect(Unit) {
        homeViewModel.loadLocation()
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        Log.e(TAG, "HomeScreen2: $locationState")

    }

    // 🔹 Load prayers ONLY when location is SUCCESS
    LaunchedEffect(locationState) {
        if (locationState is LocationUiState.Success) {
            val success = locationState as LocationUiState.Success
            prayerViewModel.load(
                lat = success.latitude, lng = success.longitude
            )
        }
    }

    BackHandler(enabled = true) {}

    Box(modifier = Modifier.fillMaxSize()) {

        HomeBackground()

        LazyColumn(
            modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item(key = "top_bar") {
                TopBarSection(
                    locationState = locationState, onLocationClick = {
                        navController.navigate("location_picker")
                    })
            }

            item { HomeHeaderSlider() }
            item { FeatureSection(navController) }
            item { PrayerTimeSection() }
            item { TodayTipSection() }
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}


@Composable
private fun HomeScreenPreviewContent(
    locationState: LocationUiState
) {
    val navController = rememberNavController()

    Box(modifier = Modifier.fillMaxSize()) {
        HomeBackground()

        LazyColumn(
            modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                TopBarSection(
                    locationState = locationState, onLocationClick = {})
            }
            item { HomeHeaderSlider() }
            item { FeatureSection(navController) }
            item { PrayerTimeSection() }
            item { TodayTipSection() }
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Preview(showBackground = true, name = "Home – Demo Location")
@Composable
private fun PreviewHome_Demo() {
    HomeScreenPreviewContent(
        locationState = LocationUiState.Success(
            city = "Kuala Lumpur",
            country = "Malaysia",
            latitude = 3.1085715,
            longitude = 101.6769629,
            source = LocationSource.DEMO,
            selectionMode = LocationSelectionMode.AUTO_DETECTED
        )
    )
}

@Preview(showBackground = true, name = "Home – GPS Location")
@Composable
private fun PreviewHome_Gps() {
    HomeScreenPreviewContent(
        locationState = LocationUiState.Success(
            city = "Mecca",
            country = "Saudi Arabia",
            latitude = 21.4225,
            longitude = 39.8262,
            source = LocationSource.GPS,
            selectionMode = LocationSelectionMode.USER_SELECTED
        )
    )
}

@Preview(showBackground = true, name = "Home – Location Loading")
@Composable
private fun PreviewHome_Loading() {
    HomeScreenPreviewContent(
        locationState = LocationUiState.Loading
    )
}

@Preview(showBackground = true, name = "Home – Location Error")
@Composable
private fun PreviewHome_Error() {
    HomeScreenPreviewContent(
        locationState = LocationUiState.Error(
            message = "Location permission denied"
        )
    )
}

@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES,
    name = "Home – Dark Mode"
)
@Composable
private fun PreviewHome_Dark() {
    HomeScreenPreviewContent(
        locationState = LocationUiState.Success(
            city = "Kuala Lumpur",
            country = "Malaysia",
            latitude = 3.1085715,
            longitude = 101.6769629,
            source = LocationSource.DEMO,
            selectionMode = LocationSelectionMode.AUTO_DETECTED
        )
    )
}




