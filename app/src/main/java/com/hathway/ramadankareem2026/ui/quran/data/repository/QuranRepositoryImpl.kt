package com.hathway.ramadankareem2026.ui.quran.data.repository

import com.hathway.ramadankareem2026.core.network.ApiResult
import com.hathway.ramadankareem2026.core.network.NetworkMonitor
import com.hathway.ramadankareem2026.ui.quran.data.local.QuranLocalDataSource
import com.hathway.ramadankareem2026.ui.quran.data.remote.QuranRemoteDataSource
import com.hathway.ramadankareem2026.ui.quran.domain.model.Ayah
import com.hathway.ramadankareem2026.ui.quran.domain.model.Surah

import com.hathway.ramadankareem2026.ui.quran.domain.repository.QuranRepository

class QuranRepositoryImpl(
    private val remote: QuranRemoteDataSource,
    private val local: QuranLocalDataSource,
    private val networkMonitor: NetworkMonitor
) : QuranRepository {


    override suspend fun getSurahList(): List<Surah> {

        if (!networkMonitor.isNetworkAvailable()) {
            return local.getSurahList()
        }

        return when (val result = remote.getSurahs()) {

            is ApiResult.Success -> {

                val surahs = result.data.map { dto ->
                    Surah(
                        id = dto.number,
                        name = dto.name,
                        englishName = dto.englishName,
                        englishNameTranslation = dto.englishNameTranslation,
                        numberOfAyahs = dto.numberOfAyahs,
                        revelationType = dto.revelationType
                    )
                }

                surahs
            }

            is ApiResult.Error -> {
                local.getSurahList()
            }
        }
    }

    override suspend fun getAyahs(surahId: Int): List<Ayah> {
        if (!networkMonitor.isNetworkAvailable()) {
            return local.getAyahs(surahId)
        }

        return when (val result = remote.getSurahDetail(surahId)) {
            is ApiResult.Success -> {
                result.data.ayahs.map { dto ->
                    Ayah(
                        number = dto.numberInSurah,
                        arabicText = dto.text,
                        translation = "",
                        audio = dto.audio
                    )
                }
            }

            is ApiResult.Error -> {
                local.getAyahs(surahId)
            }
        }
    }
}

