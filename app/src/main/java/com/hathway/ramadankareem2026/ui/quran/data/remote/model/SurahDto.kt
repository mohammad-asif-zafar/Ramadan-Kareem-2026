package com.hathway.ramadankareem2026.ui.quran.data.remote.model

data class SurahDto(
    val number: Int,
    val name: String,
    val englishName: String,
    val englishNameTranslation: String,
    val numberOfAyahs: Int,
    val revelationType: String
)
