package com.hathway.ramadankareem2026.ui.quran.route

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.ui.quran.presentation.QuranSurahListScreen
import com.hathway.ramadankareem2026.ui.quran.presentation.QuranViewModel
import com.hathway.ramadankareem2026.ui.quran.presentation.QuranViewModelFactory


@Composable
fun QuranRoute(navController: NavController) {

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
        })
}


sealed class NavRoutes(val route: String) {
    data object QuranSurahList : NavRoutes("quran")

    data object QuranSurahAyahs : NavRoutes("quran/surah/{surahId}") {
        fun createRoute(surahId: Int) = "quran/surah/$surahId"
    }

    // Backward-compatible route used by older navigation/bookmarks.
    data object QuranLegacy : NavRoutes("quran?surahId={surahId}&ayah={ayah}") {
        fun createRoute(surahId: Int, ayah: Int) = "quran?surahId=$surahId&ayah=$ayah"
    }

    data object AyahDetail : NavRoutes("ayah_detail/{ayahId}") {
        fun createRoute(ayahId: Int) = "ayah_detail/$ayahId"
    }


}
