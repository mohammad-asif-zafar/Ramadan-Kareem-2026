package com.hathway.ramadankareem2026.ui.quran.data.remote.model

data class AyahDto(
    val number: Int,
    val numberInSurah: Int,
    val text: String,
    val audio: String? = null
)
