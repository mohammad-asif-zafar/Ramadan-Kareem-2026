package com.hathway.ramadankareem2026.ui.quran.data.remote.model

data class SurahResponseDto(
    val code: Int,
    val status: String,
    val data: List<SurahDto>
)
