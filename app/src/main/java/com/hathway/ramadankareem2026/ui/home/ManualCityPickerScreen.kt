package com.hathway.ramadankareem2026.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.home.data.cityList
import com.hathway.ramadankareem2026.ui.home.homeViewModel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManualCityPickerScreen(
    navController: NavController,
    onBack: () -> Unit,
    onViewFullCalendar: () -> Unit,
    onSettings: () -> Unit
) {
    // 🔥 Share HomeViewModel with HomeScreen
    val parentEntry = remember(navController) {
        navController.getBackStackEntry("home")
    }
    val viewModel: HomeViewModel = viewModel(parentEntry)

    var query by remember { mutableStateOf("") }

    val filteredCities = remember(query) {
        cityList.filter {
            it.city.contains(query, ignoreCase = true) || it.country.contains(
                query,
                ignoreCase = true
            )
        }
    }

    Scaffold(
        topBar = {
            RamadanToolbar(
                title = stringResource(R.string.select_the_location),
                showBack = true,
                onBackClick = onBack,
                rightIcon1 = R.drawable.bell,
                onRightIcon1Click = onViewFullCalendar,
                rightIcon2 = R.drawable.bell,
                onRightIcon2Click = onSettings
            )
        }) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Search city") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn {
                items(filteredCities) { city ->
                    Text(
                        text = "${city.city}, ${city.country}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                // ✅ User explicitly selects location
                                viewModel.setUserLocation(
                                    city = city.city,
                                    country = city.country,
                                    latitude = city.lat,
                                    longitude = city.lng
                                )

                                // 🔙 Go back to Home
                                navController.popBackStack("home", inclusive = false)
                            }
                            .padding(vertical = 12.dp),
                        style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}
