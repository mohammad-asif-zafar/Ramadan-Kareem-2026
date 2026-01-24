package com.hathway.ramadankareem2026.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.hathway.ramadankareem2026.ui.allahnames.AllahNameDetailRoute
import com.hathway.ramadankareem2026.ui.allahnames.AllahNameDetailScreen
import com.hathway.ramadankareem2026.ui.allahnames.AllahNamesScreen
import com.hathway.ramadankareem2026.ui.allahnames.data.source.AllahNamesLocalData
import com.hathway.ramadankareem2026.ui.allahnames.domain.model.AllahName
import com.hathway.ramadankareem2026.ui.allahnames.viewmodel.AllahNamesViewModel
import com.hathway.ramadankareem2026.ui.components.RamadanBottomBar
import com.hathway.ramadankareem2026.ui.dua.components.DuaCategoryScreen
import com.hathway.ramadankareem2026.ui.dua.components.DuaDetailScreen
import com.hathway.ramadankareem2026.ui.dua.data.DuaRepository
import com.hathway.ramadankareem2026.ui.dua.route.DuaRoute
import com.hathway.ramadankareem2026.ui.home.HomeScreen
import com.hathway.ramadankareem2026.ui.home.LocationPickerScreen
import com.hathway.ramadankareem2026.ui.home.ManualCityPickerScreen
import com.hathway.ramadankareem2026.ui.mosques.NearbyMosquesScreen
import com.hathway.ramadankareem2026.ui.mosques.presentation.route.MosqueRoute
import com.hathway.ramadankareem2026.ui.qibla.QiblaScreen
import com.hathway.ramadankareem2026.ui.qibla.QiblaSettingsScreen
import com.hathway.ramadankareem2026.ui.splash.SplashScreen
import com.hathway.ramadankareem2026.ui.zakat.route.ZakatBreakdownRoute
import com.hathway.ramadankareem2026.ui.zakat.route.ZakatRoute

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
                QiblaSettingsScreen(
                    navController,
                    onBack = {},
                    onSettings = {},
                    onViewFullCalendar = {})
            }

            composable(Routes.RAMADAN_CALENDAR) {
                RamadanCalendarRoute(navController)
            }

            composable(
                route = "dua_detail/{duaId}", arguments = listOf(
                    navArgument("duaId") { type = NavType.StringType })
            ) { backStackEntry ->

                val duaId = backStackEntry.arguments!!.getString("duaId")!!
                val dua = DuaRepository().getDuaById(duaId)

                DuaDetailScreen(
                    dua = dua, onBack = { navController.popBackStack() })
            }

            composable(
                route = "${Routes.DUA_CATEGORY}/{categoryId}", arguments = listOf(
                    navArgument("categoryId") { type = NavType.StringType })
            ) { backStackEntry ->

                val categoryId =
                    backStackEntry.arguments?.getString("categoryId") ?: return@composable

                DuaCategoryScreen(
                    categoryId = categoryId, navController = navController
                )
            }

            // 🔹 ALLAH NAMES (NEW)
            composable(Routes.ALLAH_NAMES) {

                val viewModel: AllahNamesViewModel = viewModel()

                AllahNamesScreen(
                    names = viewModel.names,
                    onBack = { navController.popBackStack() },
                    onNameClick = { name ->
                        navController.navigate(
                            Routes.allahNameDetail(name.id)
                        )
                    })
            }

            composable(
                route = "${Routes.ALLAH_NAME_DETAIL}/{id}", arguments = listOf(
                    navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->

                val id = backStackEntry.arguments?.getInt("id") ?: return@composable

                AllahNameDetailRoute(
                    id = id, navController = navController
                )
            }
            // 🔹 ZAKAT SCREEN
            composable(Routes.ZAKAT) {
                ZakatRoute(navController = navController)
            }

            composable(Routes.ZAKAT_BREAKDOWN) {
                ZakatBreakdownRoute(navController)
            }
            composable(Routes.MOSQUES) {
                MosqueRoute(navController = navController)
            }

        }
    }
}


@Composable
fun SimpleScreen(title: String) {
    androidx.compose.material3.Text(text = title)
}


@Preview(showBackground = true)
@Composable
fun AllahNamesScreenPreview() {
    MaterialTheme {
        AllahNamesScreen(names = AllahNamesLocalData.getAll(), onBack = {}, onNameClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun AllahNameDetailPreview() {
    MaterialTheme {
        AllahNameDetailScreen(
            name = AllahName(
                id = 1,
                arabic = "الرَّحْمٰنُ",
                transliteration = "Ar-Rahman",
                english = "The Most Merciful",
                meaning = "The One who has plenty of mercy for the believers."
            ), onBack = {})
    }
}

