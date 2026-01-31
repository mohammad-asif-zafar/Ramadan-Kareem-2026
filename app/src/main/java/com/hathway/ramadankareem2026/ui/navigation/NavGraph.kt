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
import android.app.Application
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.hathway.ramadankareem2026.ui.allahnames.AllahNameDetailRoute
import com.hathway.ramadankareem2026.ui.allahnames.AllahNameDetailScreen
import com.hathway.ramadankareem2026.ui.allahnames.AllahNamesScreen
import com.hathway.ramadankareem2026.ui.allahnames.data.source.AllahNamesLocalData
import com.hathway.ramadankareem2026.ui.allahnames.domain.model.AllahName
import com.hathway.ramadankareem2026.ui.allahnames.viewmodel.AllahNamesViewModel
import com.hathway.ramadankareem2026.ui.allahnames.viewmodel.AllahNamesBookmarkViewModel
import com.hathway.ramadankareem2026.ui.allahnames.presentation.viewmodel.AllahNameBookmarkCountViewModel
import com.hathway.ramadankareem2026.ui.bookmarks.viewmodel.BookmarkCountViewModel
import com.hathway.ramadankareem2026.ui.bookmarks.route.BookmarksRoute
import com.hathway.ramadankareem2026.ui.quran.presentation.route.QuranBookmarksRoute
import com.hathway.ramadankareem2026.ui.dua.presentation.route.DuaBookmarksRoute
import com.hathway.ramadankareem2026.ui.mosques.presentation.route.MosqueBookmarksRoute
import com.hathway.ramadankareem2026.ui.allahnames.presentation.route.AllahNameBookmarksRoute
import com.hathway.ramadankareem2026.ui.components.RamadanBottomBar
import com.hathway.ramadankareem2026.ui.dua.components.DuaCategoryScreen
import com.hathway.ramadankareem2026.ui.dua.components.DuaDetailScreen
import com.hathway.ramadankareem2026.ui.dua.data.DuaRepository
import com.hathway.ramadankareem2026.ui.dua.presentation.viewmodel.DuaBookmarkCountViewModel
import com.hathway.ramadankareem2026.ui.dua.route.DuaRoute
import com.hathway.ramadankareem2026.ui.dua.viewmodel.DuaBookmarkViewModel
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
import com.hathway.ramadankareem2026.ui.quran.presentation.viewmodel.QuranBookmarkCountViewModel
import com.hathway.ramadankareem2026.ui.quran.presentation.viewmodel.QuranBookmarkViewModel
import com.hathway.ramadankareem2026.ui.quran.route.NavRoutes
import com.hathway.ramadankareem2026.ui.quran.route.QuranRoute
import com.hathway.ramadankareem2026.ui.splash.SplashScreen
import com.hathway.ramadankareem2026.ui.tips.presentation.screen.TipDetailScreen
import com.hathway.ramadankareem2026.ui.tips.presentation.screen.TipsScreen
import com.hathway.ramadankareem2026.ui.zakat.route.ZakatBreakdownRoute
import com.hathway.ramadankareem2026.ui.zakat.route.ZakatHistoryRoute
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
        Routes.HOME, Routes.QURAN, Routes.QIBLA
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
                
                // Create shared ViewModels for immediate updates
                val duaBookmarkCountViewModel: DuaBookmarkCountViewModel = viewModel()
                val sharedBookmarkViewModel: DuaBookmarkViewModel = viewModel()

                DuaDetailScreen(
                    dua = dua, 
                    onBack = { navController.popBackStack() },
                    navController = navController,
                    bookmarkViewModel = sharedBookmarkViewModel,
                    duaBookmarkCountViewModel = duaBookmarkCountViewModel
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
                val allahNameBookmarkCountViewModel: AllahNameBookmarkCountViewModel = viewModel()
                val sharedBookmarkViewModel: AllahNamesBookmarkViewModel = viewModel()

                AllahNamesScreen(
                    names = viewModel.names,
                    onBack = { navController.popBackStack() },
                    onNameClick = { name ->
                        navController.navigate(
                            Routes.allahNameDetail(name.id)
                        )
                    },
                    navController = navController,
                    allahNameBookmarkCountViewModel = allahNameBookmarkCountViewModel)
            }

            composable(
                route = "${Routes.ALLAH_NAME_DETAIL}/{id}", arguments = listOf(
                    navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->

                val id = backStackEntry.arguments?.getInt("id") ?: return@composable
                // Create shared ViewModels for immediate updates
                val allahNameBookmarkCountViewModel: AllahNameBookmarkCountViewModel = viewModel()
                val sharedBookmarkViewModel: AllahNamesBookmarkViewModel = viewModel()

                AllahNameDetailRoute(
                    id = id, 
                    navController = navController, 
                    allahNameBookmarkCountViewModel = allahNameBookmarkCountViewModel,
                    sharedBookmarkViewModel = sharedBookmarkViewModel
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

            // Content-specific bookmark routes
            composable(Routes.QURAN_BOOKMARKS) {
                QuranBookmarksRoute(navController = navController)
            }

            composable(Routes.DUA_BOOKMARKS) {
                DuaBookmarksRoute(navController = navController)
            }

            composable(Routes.MOSQUE_BOOKMARKS) {
                MosqueBookmarksRoute(navController = navController)
            }

            composable(Routes.ALLAH_NAME_BOOKMARKS) {
                AllahNameBookmarksRoute(navController = navController)
            }

            composable(Routes.ZAKAT_HISTORY) {
                ZakatHistoryRoute(navController)
            }

            composable(Routes.QURAN) {
                QuranRoute(navController)
            }

            composable(Routes.TIPS) {
                TipsScreen(
                    onBack = { navController.popBackStack() },
                    onTipClick = { tipId ->
                        navController.navigate("tip_detail/$tipId")
                    }
                )
            }

            composable(
                route = "tip_detail/{tipId}",
                arguments = listOf(navArgument("tipId") { type = NavType.IntType })
            ) { backStackEntry ->
                val tipId = backStackEntry.arguments?.getInt("tipId") ?: return@composable
                TipDetailScreen(
                    tipId = tipId,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(
                route = NavRoutes.QuranSurahAyahs.route,
                arguments = listOf(navArgument("surahId") { type = NavType.IntType })
            ) { backStackEntry ->
                val context = LocalContext.current
                val surahId = backStackEntry.arguments?.getInt("surahId") ?: return@composable

                val viewModel: QuranViewModel = viewModel(
                    factory = QuranViewModelFactory(
                        context = context.applicationContext
                    )
                )
                val quranBookmarkCountViewModel: QuranBookmarkCountViewModel = viewModel()
                val quranBookmarkViewModel: QuranBookmarkViewModel = viewModel()

                QuranSurahAyahsScreen(
                    viewModel = viewModel,
                    surahId = surahId,
                    onBack = { navController.popBackStack() },
                    quranBookmarkViewModel = quranBookmarkViewModel,
                    quranBookmarkCountViewModel = quranBookmarkCountViewModel,
                    navController = navController
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
                val quranBookmarkCountViewModel: QuranBookmarkCountViewModel = viewModel()
                val quranBookmarkViewModel: QuranBookmarkViewModel = viewModel()

                if (surahIdArg > 0) {
                    QuranSurahAyahsScreen(
                        viewModel = viewModel,
                        surahId = surahIdArg,
                        onBack = { navController.popBackStack() },
                        quranBookmarkViewModel = quranBookmarkViewModel,
                        quranBookmarkCountViewModel = quranBookmarkCountViewModel,
                        navController = navController
                    )
                } else {
                    QuranSurahListScreen(
                        viewModel = viewModel,
                        quranBookmarkCountViewModel = quranBookmarkCountViewModel,
                        onBack = { navController.popBackStack() },
                        onSurahClick = { surah ->
                            navController.navigate(
                                com.hathway.ramadankareem2026.ui.quran.route.NavRoutes.QuranSurahAyahs.createRoute(
                                    surah.id
                                )
                            )
                        },
                        navController = navController
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
        val mockAllahNameBookmarkCountViewModel = AllahNameBookmarkCountViewModel(android.app.Application())
        AllahNamesScreen(
            names = AllahNamesLocalData.getAll(), 
            onBack = {}, 
            onNameClick = {},
            navController = rememberNavController(),
            allahNameBookmarkCountViewModel = mockAllahNameBookmarkCountViewModel)
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
            ), 
            onBack = {},
            navController = rememberNavController(),
            bookmarkViewModel = AllahNamesBookmarkViewModel(android.app.Application()),
            allahNameBookmarkCountViewModel = AllahNameBookmarkCountViewModel(android.app.Application()))
    }
}

