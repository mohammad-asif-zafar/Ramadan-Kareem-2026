package com.hathway.ramadankareem2026.ui.dua.model

data class DuaItem(
    val id: String = "",
    val title: String = "",
    val arabic: String = "",
    val translation: String = "",
    val source: String = "",
    val categoryId: String = "",
    val transliteration: String = "",
    val audioResId: Int? = null
)
