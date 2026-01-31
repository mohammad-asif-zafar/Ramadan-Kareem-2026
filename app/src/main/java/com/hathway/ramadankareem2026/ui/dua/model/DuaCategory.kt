package com.hathway.ramadankareem2026.ui.dua.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Mosque
import androidx.compose.ui.graphics.vector.ImageVector

data class DuaCategory(
    val id: String  = "",
    val title: String = "",
    val subtitle: String = "",
    val icon: ImageVector  = Icons.Outlined.Mosque
)
