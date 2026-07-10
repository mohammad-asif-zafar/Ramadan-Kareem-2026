package com.hathway.ramadankareem2026.ui.home.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.hathway.ramadankareem2026.ui.theme.Emerald


data class FeatureModel(
    @StringRes val titleRes: Int,
    val icon: FeatureIcon,
    val route: String,
    val color: Color = Emerald
)