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
import com.hathway.ramadankareem2026.ui.dua.route.DuaRoute
import com.hathway.ramadankareem2026.ui.home.HomeScreen
import com.hathway.ramadankareem2026.ui.home.LocationPickerScreen
import com.hathway.ramadankareem2026.ui.home.ManualCityPickerScreen
import com.hathway.ramadankareem2026.ui.qibla.QiblaScreen
import com.hathway.ramadankareem2026.ui.qibla.QiblaSettingsScreen
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

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val isBottomBarVisible = currentRoute in setOf(
        Routes.HOME, Routes.QURAN, Routes.QIBLA, Routes.TASBIH
    )

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

            composable(Routes.HOME) {
                HomeScreen(navController)
            }

            composable(Routes.QURAN) {
                SimpleScreen(Routes.QURAN)
            }

            composable(Routes.QIBLA) {
                QiblaScreen(
                    title = Routes.QIBLA,
                    navController = navController,
                    onBack = {},
                    onSettings = {},
                    onViewFullCalendar = {})
            }

            composable(Routes.TASBIH) {
                SimpleScreen(Routes.TASBIH)
            }

            // 👇 Bottom bar hidden automatically
            composable(Routes.DUA) {
                DuaRoute(routeName = "", navController = navController)
            }

            composable(Routes.CALENDAR) {
                SimpleScreen(Routes.CALENDAR)
            }

            composable("location_picker") {
                LocationPickerScreen(navController, {}, {}, {})
            }

            composable("manual_city_picker") {
                ManualCityPickerScreen(navController, {}, {}, {})
            }

            composable(Routes.QIBLA_SETTINGS) {
                QiblaSettingsScreen(navController, onBack = {}, onSettings = {}, onViewFullCalendar = {})
            }

            composable(Routes.RAMADAN_CALENDAR) {
                RamadanCalendarRoute(navController)
            }
        }
    }
}


@Composable
fun SimpleScreen(title: String) {
    androidx.compose.material3.Text(text = title)
}
