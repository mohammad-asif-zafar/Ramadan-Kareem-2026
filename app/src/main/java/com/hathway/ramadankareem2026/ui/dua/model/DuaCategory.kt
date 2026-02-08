package com.hathway.ramadankareem2026.ui.dua.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Mosque
import androidx.compose.ui.graphics.vector.ImageVector

data class LocalizedCategoryNames(
    val english: String = "",
    val hindi: String = "",
    val urdu: String = "",
    val malaysian: String = ""
) {
    fun getName(language: String): String {
        return when (language.lowercase()) {
            "hi", "hindi" -> if (hindi.isNotBlank()) hindi else english
            "ur", "urdu" -> if (urdu.isNotBlank()) urdu else english
            "ms", "malaysian" -> if (malaysian.isNotBlank()) malaysian else english
            else -> english
        }
    }
}

data class DuaCategory(
    val id: String  = "",
    val title: LocalizedCategoryNames = LocalizedCategoryNames(),
    val subtitle: LocalizedCategoryNames = LocalizedCategoryNames(),
    val icon: ImageVector  = Icons.Outlined.Mosque
)
