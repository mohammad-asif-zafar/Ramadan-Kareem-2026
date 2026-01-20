package com.hathway.ramadankareem2026.ui.home

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.home.homeViewModel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationPickerScreen(
    navController: NavController,
    onBack: () -> Unit,
    onViewFullCalendar: () -> Unit,
    onSettings: () -> Unit
) {
    // 🔥 SHARE HomeViewModel with HomeScreen
    val parentEntry = remember(navController) {
        navController.getBackStackEntry("home")
    }
    val viewModel: HomeViewModel = viewModel(parentEntry)

    val permissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            if (granted) {
                viewModel.loadLocation()
                navController.popBackStack() // return to Home
            }
        }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Select Location") }
            )
            RamadanToolbar(
                title = stringResource(R.string.feature_dua),
                showBack = true,
                onBackClick = onBack,
                rightIcon1 =  R.drawable.bell,
                onRightIcon1Click = onViewFullCalendar,
                rightIcon2 =  R.drawable.bell,
                onRightIcon2Click = onSettings
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(
                onClick = {
                    // TODO: manual city search
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Search city manually")
            }

            Button(
                onClick = {
                    permissionLauncher.launch(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Use my current location")
            }
        }
    }
}
