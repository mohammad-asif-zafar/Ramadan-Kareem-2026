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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.ui.home.data.cityList
import com.hathway.ramadankareem2026.ui.home.homeViewModel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManualCityPickerScreen(
    navController: NavController
) {
    // 🔥 Share HomeViewModel
    val parentEntry = remember(navController) {
        navController.getBackStackEntry("home")
    }
    val viewModel: HomeViewModel = viewModel(parentEntry)

    var query by remember { mutableStateOf("") }

    val filteredCities = cityList.filter {
        it.city.contains(query, ignoreCase = true)
                || it.country.contains(query, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Select City") })
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
                                viewModel.setUserLocation(
                                    city = city.city,
                                    country = city.country,
                                    lat = city.lat,
                                    lng = city.lng
                                )
                                navController.popBackStack("home", false)
                            }
                            .padding(vertical = 12.dp),
                        style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}
