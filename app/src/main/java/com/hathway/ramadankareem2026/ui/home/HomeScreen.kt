package com.hathway.ramadankareem2026.ui.home

import android.Manifest
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.ui.home.components.FeatureSection
import com.hathway.ramadankareem2026.ui.home.components.HomeHeaderSlider
import com.hathway.ramadankareem2026.ui.home.components.PrayerTimeSection
import com.hathway.ramadankareem2026.ui.home.components.TodayTipSection
import com.hathway.ramadankareem2026.ui.home.components.TopBarSection
import com.hathway.ramadankareem2026.ui.home.homeViewModel.HomeViewModel
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.hathway.ramadankareem2026.ui.home.data.LocationUiState
import com.hathway.ramadankareem2026.ui.home.data.LocationSource


@Composable
fun HomeScreen(
    navController: NavController
) {
    val viewModel: HomeViewModel = viewModel()
    val locationState by viewModel.locationState.collectAsState()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) viewModel.loadLocation()
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    // 🔒 Disable back press & back gesture on Home
    BackHandler(enabled = true) {
        // Do nothing → prevents back navigation
    }

    Box(modifier = Modifier.fillMaxSize()) {

        HomeBackground()

        LazyColumn(
            modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                TopBarSection(
                    locationState = locationState, onLocationClick = {
                        // 👇 user explicitly tapped location
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
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                TopBarSection(
                    locationState = locationState,
                    onLocationClick = {}
                )
            }
            item { HomeHeaderSlider() }
            item { FeatureSection(navController) }
            item { PrayerTimeSection() }
            item { TodayTipSection() }
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}
@Preview(showBackground = true, name = "Home – Auto Location")
@Composable
private fun PreviewHome_AutoLocation() {
    HomeScreenPreviewContent(
        locationState = LocationUiState(
            city = "Kuala Lumpur",
            country = "Malaysia",
            latitude = 3.1390,
            longitude = 101.6869,
            source = LocationSource.AUTO_DETECTED
        )
    )
}
@Preview(showBackground = true, name = "Home – User Selected")
@Composable
private fun PreviewHome_UserSelected() {
    HomeScreenPreviewContent(
        locationState = LocationUiState(
            city = "Mecca",
            country = "Saudi Arabia",
            source = LocationSource.USER_SELECTED
        )
    )
}

@Preview(showBackground = true, name = "Home – Location Loading")
@Composable
private fun PreviewHome_Loading() {
    HomeScreenPreviewContent(
        locationState = LocationUiState(
            source = LocationSource.NONE
        )
    )
}

@Preview(showBackground = true, name = "Home – Location Error")
@Composable
private fun PreviewHome_Error() {
    HomeScreenPreviewContent(
        locationState = LocationUiState(
            error = "Location permission denied",
            source = LocationSource.NONE
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
        locationState = LocationUiState(
            city = "Kuala Lumpur",
            country = "Malaysia",
            source = LocationSource.AUTO_DETECTED
        )
    )
}

