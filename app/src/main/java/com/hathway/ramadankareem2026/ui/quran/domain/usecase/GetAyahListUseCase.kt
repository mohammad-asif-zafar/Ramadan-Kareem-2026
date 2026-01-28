package com.hathway.ramadankareem2026.ui.quran.domain.usecase

import com.hathway.ramadankareem2026.ui.quran.domain.model.Ayah
import com.hathway.ramadankareem2026.ui.quran.domain.repository.QuranRepository

class GetAyahListUseCase(
    private val repository: QuranRepository
) {
    suspend operator fun invoke(surahId: Int): List<Ayah> {
        return repository.getAyahs(surahId)
    }
}

