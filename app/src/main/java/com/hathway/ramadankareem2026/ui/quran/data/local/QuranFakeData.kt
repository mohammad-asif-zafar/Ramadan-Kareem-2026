package com.hathway.ramadankareem2026.ui.quran.data.local

import com.hathway.ramadankareem2026.ui.quran.domain.model.Ayah
import com.hathway.ramadankareem2026.ui.quran.domain.model.Surah


object QuranFakeData {
    val surahList = listOf(
        Surah(
            id = 1,
            name = "سُورَةُ ٱلْفَاتِحَةِ",
            englishName = "Al-Faatiha",
            numberOfAyahs = 7,
            englishNameTranslation = "The Opening",
            revelationType = "Meccan"
        ),
        Surah(
            2,
            "سُورَةُ البَقَرَةِ",
            "Al-Baqara",
            numberOfAyahs = 286,
            englishNameTranslation = "Medinan"
        ),
        Surah(
            3,
            "سُورَةُ آلِ عِمۡرَانَ",
            "Aal-i-Imraan",
            numberOfAyahs = 200,
            englishNameTranslation = "Medinan"
        ),
        Surah(
            4,
            "سُورَةُ النِّسَاءِ",
            "An-Nisaa",
            numberOfAyahs = 176,
            englishNameTranslation = "Medinan"
        ),
        Surah(
            5,
            "سُورَةُ المَائـِدَةِ",
            "Al-Maaida",
            numberOfAyahs = 120,
            englishNameTranslation = "Medinan"
        ),
        Surah(
            6,
            "سُورَةُ الأَنۡعَامِ",
            "Al-An'aam",
            numberOfAyahs = 165,
            englishNameTranslation = "Meccan"
        ),
        Surah(
            7,
            "سُورَةُ الأَعۡرَافِ",
            "Al-A'raaf",
            numberOfAyahs = 206,
            englishNameTranslation = "Meccan"
        ),
        Surah(
            8,
            "سُورَةُ الأَنفَالِ",
            "Al-Anfaal",
            numberOfAyahs = 75,
            englishNameTranslation = "Medinan"
        ),
        Surah(
            9,
            "سُورَةُ التَّوۡبَةِ",
            "At-Tawba",
            numberOfAyahs = 129,
            englishNameTranslation = "Medinan"
        ),
        Surah(
            10, "سُورَةُ يُونُسَ", "Yunus", numberOfAyahs = 109, englishNameTranslation = "Meccan"
        ),
    )

    fun ayahs(surahId: Int): List<Ayah> {
        return listOf(
            Ayah(1, "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ", "In the name of Allah"),
            Ayah(2, "الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ", "All praise is for Allah"),
            Ayah(3, "الرَّحْمَٰنِ الرَّحِيمِ", "The Most Merciful"),
        )
    }
}
