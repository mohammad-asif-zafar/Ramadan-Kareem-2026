package com.hathway.ramadankareem2026.ui.quran.data.remote

import com.hathway.ramadankareem2026.core.network.ApiConfig
import com.hathway.ramadankareem2026.core.network.ApiResult
import com.hathway.ramadankareem2026.core.network.NetworkClient
import com.hathway.ramadankareem2026.core.network.safeApiCall
import com.hathway.ramadankareem2026.ui.quran.data.remote.model.SurahDto

class QuranRemoteDataSource {

    private val api: QuranApi = NetworkClient.create(
        service = QuranApi::class.java,
        baseUrl = ApiConfig.QURAN_BASE_URL
    )

    suspend fun getSurahs(): ApiResult<List<SurahDto>> {
        return when (val result = safeApiCall { api.getSurahs() }) {

            is ApiResult.Success -> {
                ApiResult.Success(result.data.data) // 🔑 IMPORTANT
            }

            is ApiResult.Error -> result
        }
    }
}


