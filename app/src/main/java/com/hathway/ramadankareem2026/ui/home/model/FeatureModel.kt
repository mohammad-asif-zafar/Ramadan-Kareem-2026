package com.hathway.ramadankareem2026.ui.home.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector


data class FeatureModel(
    @StringRes val titleRes: Int,
    val icon: FeatureIcon,
    val route: String
)