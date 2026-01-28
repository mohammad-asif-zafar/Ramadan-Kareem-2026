package com.hathway.ramadankareem2026.ui.quran.data.remote

import com.hathway.ramadankareem2026.core.network.BaseResponse
import com.hathway.ramadankareem2026.ui.quran.data.remote.model.SurahDetailDto
import com.hathway.ramadankareem2026.ui.quran.data.remote.model.SurahDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface QuranApi {
    @GET("surah")
    suspend fun getSurahs(): Response<BaseResponse<List<SurahDto>>>

    @GET("surah/{surahId}/{edition}")
    suspend fun getSurahDetail(
        @Path("surahId") surahId: Int,
        @Path("edition", encoded = true) edition: String
    ): Response<BaseResponse<SurahDetailDto>>

}
