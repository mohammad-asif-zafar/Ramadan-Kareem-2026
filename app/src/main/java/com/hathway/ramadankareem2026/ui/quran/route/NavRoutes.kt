package com.hathway.ramadankareem2026.ui.quran.route

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.ui.quran.presentation.QuranSurahListScreen
import com.hathway.ramadankareem2026.ui.quran.presentation.QuranViewModel
import com.hathway.ramadankareem2026.ui.quran.presentation.QuranViewModelFactory
import com.hathway.ramadankareem2026.ui.quran.presentation.viewmodel.QuranBookmarkCountViewModel


@Composable
fun QuranRoute(navController: NavController) {

    val context = LocalContext.current
    val viewModel: QuranViewModel = viewModel(
        factory = QuranViewModelFactory(
            context = context.applicationContext
        )
    )
    val quranBookmarkCountViewModel: QuranBookmarkCountViewModel = viewModel()

    QuranSurahListScreen(
        viewModel = viewModel,
        quranBookmarkCountViewModel = quranBookmarkCountViewModel,
        onBack = { navController.popBackStack() },
        onSurahClick = { surah ->
            navController.navigate(
                NavRoutes.QuranSurahAyahs.createRoute(
                    surah.id
                )
            )
        },
        navController = navController)
}


sealed class NavRoutes(val route: String) {

    data object QuranSurahAyahs : NavRoutes("quran/surah/{surahId}") {
        fun createRoute(surahId: Int) = "quran/surah/$surahId"
    }


}
