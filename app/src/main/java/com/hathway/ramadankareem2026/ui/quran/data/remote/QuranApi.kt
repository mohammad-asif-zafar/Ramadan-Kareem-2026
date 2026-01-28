package com.hathway.ramadankareem2026.ui.quran.data.remote

import com.hathway.ramadankareem2026.ui.quran.data.remote.model.SurahResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface QuranApi {
    @GET("surah")
    suspend fun getSurahs(): Response<SurahResponseDto>

}
