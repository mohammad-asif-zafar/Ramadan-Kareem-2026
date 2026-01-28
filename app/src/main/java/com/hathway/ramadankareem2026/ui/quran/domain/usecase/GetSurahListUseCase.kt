package com.hathway.ramadankareem2026.ui.quran.domain.usecase

import com.hathway.ramadankareem2026.ui.quran.domain.model.Surah
import com.hathway.ramadankareem2026.ui.quran.domain.repository.QuranRepository

class GetSurahListUseCase(
    private val repository: QuranRepository
) {
    suspend operator fun invoke(): List<Surah> {
        return repository.getSurahList()
    }
}
