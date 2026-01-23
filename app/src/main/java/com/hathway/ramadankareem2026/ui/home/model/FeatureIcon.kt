package com.hathway.ramadankareem2026.ui.home.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector

sealed class FeatureIcon {
    data class Vector(val imageVector: ImageVector) : FeatureIcon()
    data class Drawable(@DrawableRes val resId: Int) : FeatureIcon()
}
