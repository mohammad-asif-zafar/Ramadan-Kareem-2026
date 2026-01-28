package com.hathway.ramadankareem2026.ui.quran.domain.model

data class Ayah(
    val number: Int,
    val arabicText: String,
    val translation: String,
    val audio: String? = null
)
