package com.hathway.ramadankareem2026.ui.home

import android.Manifest
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hathway.ramadankareem2026.ui.home.components.*
import com.hathway.ramadankareem2026.ui.home.homeViewModel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController, listState: LazyListState
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
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 24.dp)
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
