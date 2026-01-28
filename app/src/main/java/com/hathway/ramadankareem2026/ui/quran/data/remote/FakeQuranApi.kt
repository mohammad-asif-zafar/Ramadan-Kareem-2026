package com.hathway.ramadankareem2026.ui.quran.data.remote

import com.hathway.ramadankareem2026.ui.quran.data.remote.QuranApi
import com.hathway.ramadankareem2026.ui.quran.data.remote.model.SurahDto
import com.hathway.ramadankareem2026.ui.quran.data.remote.model.SurahResponseDto
import retrofit2.Response

class FakeQuranApi(
    private val shouldFail: Boolean = false
) : QuranApi {

    override suspend fun getSurahs(): Response<SurahResponseDto> {
        return if (shouldFail) {
            Response.error(
                500,
                okhttp3.ResponseBody.create(null, "Server error")
            )
        } else {
            Response.success(
                SurahResponseDto(
                    code = 200,
                    status = "OK",
                    data = listOf(
                        SurahDto(
                            number = 1,
                            name = "سُورَةُ ٱلْفَاتِحَةِ",
                            englishName = "Al-Faatiha",
                            englishNameTranslation = "The Opening",
                            numberOfAyahs = 7,
                            revelationType = "Meccan"
                        )
                    )
                )
            )
        }
    }
}
