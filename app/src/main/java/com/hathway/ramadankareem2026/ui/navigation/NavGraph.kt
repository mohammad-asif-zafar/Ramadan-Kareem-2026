package com.hathway.ramadankareem2026.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.hathway.ramadankareem2026.ui.components.RamadanBottomBar
import com.hathway.ramadankareem2026.ui.dua.DuaScreen
import com.hathway.ramadankareem2026.ui.home.HomeScreen
import com.hathway.ramadankareem2026.ui.home.LocationPickerScreen
import com.hathway.ramadankareem2026.ui.home.ManualCityPickerScreen
import com.hathway.ramadankareem2026.ui.qibla.QiblaScreen
import com.hathway.ramadankareem2026.ui.splash.SplashScreen

@Composable
fun NavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = "fake_splash"
    ) {

        composable("fake_splash") {
            SplashScreen {
                navController.navigate("home_root") {
                    popUpTo("fake_splash") { inclusive = true }
                }
            }
        }

        composable("home_root") {
            HomeScaffold()
        }
    }
}

@Composable
private fun HomeScaffold() {

    val navController = rememberNavController()
    val listState = rememberLazyListState()

    val isBottomBarVisible by remember {
        derivedStateOf {
            listState.firstVisibleItemScrollOffset == 0 || listState.firstVisibleItemIndex == 0
        }
    }
    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = isBottomBarVisible,
                enter = slideInVertically { it },
                exit = slideOutVertically { it }) {
                RamadanBottomBar(navController)
            }

        }) { padding ->

        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(padding)
        ) {

            composable(route = Routes.HOME) {
                HomeScreen(
                    navController, listState = listState
                ) // 👈 pass it)
            }

            composable(route = Routes.QURAN) {
                SimpleScreen(title = Routes.QURAN)
            }

            composable(route = Routes.QIBLA) {
                SimpleScreen(title = Routes.QIBLA)
            }

            composable(route = Routes.TASBIH) {
                SimpleScreen(title = Routes.TASBIH)
            }
            composable(Routes.DUA) { DuaScreen(Routes.DUA, navController = navController) }
            composable(Routes.ZAKAT) { SimpleScreen(Routes.ZAKAT) }
            composable(Routes.REMINDER) { SimpleScreen(Routes.REMINDER) }
            composable(Routes.TIPS) { SimpleScreen(Routes.TIPS) }
            composable(Routes.TASBIH) { SimpleScreen(Routes.TASBIH) }
            composable(Routes.QIBLA) { QiblaScreen(title = Routes.QIBLA) }
            composable(Routes.QURAN) { SimpleScreen(Routes.QURAN) }
            composable(Routes.CALENDAR) { SimpleScreen(Routes.CALENDAR) }
            composable("location_picker") {
                LocationPickerScreen(navController)
            }
            composable("manual_city_picker") {
                ManualCityPickerScreen(navController)
            }
        }
    }
}

@Composable
fun SimpleScreen(title: String) {
    androidx.compose.material3.Text(text = title)
}
