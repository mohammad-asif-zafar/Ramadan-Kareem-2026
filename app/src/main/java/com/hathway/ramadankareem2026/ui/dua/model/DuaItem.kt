package com.hathway.ramadankareem2026.ui.dua.model

data class LocalizedDuaText(
    val english: String = "",
    val hindi: String = "",
    val urdu: String = "",
    val malaysian: String = ""
) {
    fun getText(language: String): String {
        return when (language.lowercase()) {
            "hi", "hindi" -> if (hindi.isNotBlank()) hindi else english
            "ur", "urdu" -> if (urdu.isNotBlank()) urdu else english
            "ms", "malaysian" -> if (malaysian.isNotBlank()) malaysian else english
            else -> english
        }
    }
}

data class DuaItem(
    val id: String = "",
    val title: LocalizedDuaText = LocalizedDuaText(),
    val arabic: String = "",
    val translation: LocalizedDuaText = LocalizedDuaText(),
    val source: String = "",
    val categoryId: String = "",
    val transliteration: String = "",
    val audioResId: Int? = null
)
