package com.hathway.ramadankareem2026.ui.quran.data.local

import com.hathway.ramadankareem2026.ui.quran.domain.model.Ayah
import com.hathway.ramadankareem2026.ui.quran.domain.model.Surah

class QuranLocalDataSource {

    fun getSurahList(): List<Surah> {
        return QuranFakeData.surahList   // static list / Room later
    }

    fun saveSurahs(surahs: List<Surah>) {
        // no-op for now (Room later)
    }

    fun getAyahs(surahId: Int): List<Ayah> {
        val ayahs = QuranFakeData.ayahs(surahId)
        println("QuranLocalDataSource → surahId=$surahId, ayahs=${ayahs.size}")
        return ayahs
    }

}
