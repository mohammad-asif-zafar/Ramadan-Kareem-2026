package com.hathway.ramadankareem2026.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.hathway.ramadankareem2026.ui.bookmarks.route.BookmarksRoute
import com.hathway.ramadankareem2026.ui.components.RamadanBottomBar
import com.hathway.ramadankareem2026.ui.dua.components.DuaCategoryScreen
import com.hathway.ramadankareem2026.ui.dua.components.DuaDetailScreen
import com.hathway.ramadankareem2026.ui.dua.data.DuaRepository
import com.hathway.ramadankareem2026.ui.dua.route.DuaRoute
import com.hathway.ramadankareem2026.ui.home.HomeScreen
import com.hathway.ramadankareem2026.ui.home.LocationPickerScreen
import com.hathway.ramadankareem2026.ui.home.ManualCityPickerScreen
import com.hathway.ramadankareem2026.ui.mosques.presentation.route.MosqueRoute
import com.hathway.ramadankareem2026.ui.qibla.QiblaScreen
import com.hathway.ramadankareem2026.ui.qibla.QiblaSettingsScreen
import com.hathway.ramadankareem2026.ui.quran.presentation.QuranSurahAyahsScreen
import com.hathway.ramadankareem2026.ui.quran.presentation.QuranSurahListScreen
import com.hathway.ramadankareem2026.ui.quran.presentation.QuranViewModel
import com.hathway.ramadankareem2026.ui.quran.presentation.QuranViewModelFactory
import com.hathway.ramadankareem2026.ui.quran.route.NavRoutes
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
                    dua = dua, 
                    onBack = { navController.popBackStack() },
                    navController = navController
                )
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

            composable(Routes.ZAKAT) {
                ZakatRoute(navController = navController)
            }

            composable(Routes.ZAKAT_BREAKDOWN) {
                ZakatBreakdownRoute(navController)
            }
            composable(Routes.MOSQUES) {
                MosqueRoute(navController = navController)
            }

            composable(Routes.BOOKMARKS) {
                BookmarksRoute(navController)
            }

            composable(Routes.QURAN) {
                val context = LocalContext.current
                val viewModel: QuranViewModel = viewModel(
                    factory = QuranViewModelFactory(
                        context = context.applicationContext
                    )
                )

                QuranSurahListScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() },
                    onSurahClick = { surah ->
                        navController.navigate(
                            com.hathway.ramadankareem2026.ui.quran.route.NavRoutes.QuranSurahAyahs.createRoute(
                                surah.id
                            )
                        )
                    }
                )
            }

            composable(
                route = com.hathway.ramadankareem2026.ui.quran.route.NavRoutes.QuranSurahAyahs.route,
                arguments = listOf(navArgument("surahId") { type = NavType.IntType })
            ) { backStackEntry ->
                val context = LocalContext.current
                val surahId = backStackEntry.arguments?.getInt("surahId") ?: return@composable

                val viewModel: QuranViewModel = viewModel(
                    factory = QuranViewModelFactory(
                        context = context.applicationContext
                    )
                )

                QuranSurahAyahsScreen(
                    viewModel = viewModel,
                    surahId = surahId,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(route = NavRoutes.QuranLegacy.route, arguments = listOf(navArgument("surahId") {
                type = NavType.IntType
                defaultValue = -1
            }, navArgument("ayah") {
                type = NavType.IntType
                defaultValue = -1
            })) { backStackEntry ->

                val context = LocalContext.current

                val surahIdArg = backStackEntry.arguments?.getInt("surahId") ?: -1
                val ayahArg = backStackEntry.arguments?.getInt("ayah") ?: -1

                val viewModel: QuranViewModel = viewModel(
                    factory = QuranViewModelFactory(
                        context = context.applicationContext
                    )
                )

                if (surahIdArg > 0) {
                    QuranSurahAyahsScreen(
                        viewModel = viewModel,
                        surahId = surahIdArg,
                        onBack = { navController.popBackStack() }
                    )
                } else {
                    QuranSurahListScreen(
                        viewModel = viewModel,
                        onBack = { navController.popBackStack() },
                        onSurahClick = { surah ->
                            navController.navigate(
                                com.hathway.ramadankareem2026.ui.quran.route.NavRoutes.QuranSurahAyahs.createRoute(
                                    surah.id
                                )
                            )
                        }
                    )
                }
            }

            composable(
                route = NavRoutes.AyahDetail.route,
                arguments = listOf(
                    navArgument("ayahId") { type = NavType.IntType }
                )
            ) { backStackEntry ->

                val ayahId = backStackEntry.arguments?.getInt("ayahId")
                    ?: return@composable

                AyahDetailScreen(
                    ayahId = ayahId,
                    onBack = { navController.popBackStack() }
                )
            }

        }
    }
}

@Composable
fun AyahDetailScreen(ayahId: Int, onBack: () -> Boolean) {
    TODO("Not yet implemented")
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

