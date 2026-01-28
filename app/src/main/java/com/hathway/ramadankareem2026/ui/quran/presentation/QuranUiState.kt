package com.hathway.ramadankareem2026.ui.quran.presentation

import com.hathway.ramadankareem2026.ui.quran.domain.model.Ayah
import com.hathway.ramadankareem2026.ui.quran.domain.model.Surah

data class QuranUiState(
    val isLoading: Boolean = false,
    val surahList: List<Surah> = emptyList(),
    val selectedSurah: Surah? = null,
    val ayahs: List<Ayah> = emptyList(),
    val errorMessage: String? = null
)
