package com.hathway.ramadankareem2026.ui.quran.route

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
