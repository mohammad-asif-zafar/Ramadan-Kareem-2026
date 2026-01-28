package com.hathway.ramadankareem2026.ui.quran.domain.repository

import com.hathway.ramadankareem2026.ui.quran.domain.model.Ayah
import com.hathway.ramadankareem2026.ui.quran.domain.model.Surah

interface QuranRepository {

    suspend fun getSurahList(): List<Surah>

    suspend fun getAyahs(surahId: Int): List<Ayah>
}
