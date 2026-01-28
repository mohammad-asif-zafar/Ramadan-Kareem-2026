package com.hathway.ramadankareem2026.ui.quran.data.repository

import android.util.Log
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

    private val TAG = "QuranRepositoryImpl"

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

                local.saveSurahs(surahs)
                surahs
            }

            is ApiResult.Error -> {
                Log.e(TAG, "Remote error: ${result.message}")
                local.getSurahList()
            }
        }
    }

    override suspend fun getAyahs(surahId: Int): List<Ayah> {
        // ✅ REQUIRED
        return local.getAyahs(surahId)
    }
}

