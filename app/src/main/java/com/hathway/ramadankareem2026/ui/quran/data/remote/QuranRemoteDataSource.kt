package com.hathway.ramadankareem2026.ui.quran.data.remote

import com.hathway.ramadankareem2026.core.network.ApiResult
import com.hathway.ramadankareem2026.core.network.NetworkClient
import com.hathway.ramadankareem2026.core.network.safeApiCall
import com.hathway.ramadankareem2026.core.network.map
import com.hathway.ramadankareem2026.ui.quran.data.remote.model.SurahDetailDto
import com.hathway.ramadankareem2026.ui.quran.data.remote.model.SurahDto

class QuranRemoteDataSource {

    private companion object {
        const val BASE_URL = "https://api.alquran.cloud/v1/"
        const val DEFAULT_EDITION = "ar.alafasy"
    }

    private val api: QuranApi = NetworkClient.create(
        service = QuranApi::class.java,
        baseUrl = BASE_URL
    )

    suspend fun getSurahs(): ApiResult<List<SurahDto>> {
        return safeApiCall { api.getSurahs() }
            .map { it.data }
    }

    suspend fun getSurahDetail(
        surahId: Int,
        edition: String = DEFAULT_EDITION
    ): ApiResult<SurahDetailDto> {
        return safeApiCall { api.getSurahDetail(surahId = surahId, edition = edition) }
            .map { it.data }
    }
}


