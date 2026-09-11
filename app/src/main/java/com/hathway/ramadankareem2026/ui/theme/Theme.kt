package com.hathway.ramadankareem2026.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(

    primary = Emerald,
    onPrimary = Color.White,

    primaryContainer = EmeraldContainer,
    onPrimaryContainer = EmeraldDark,

    secondary = Gold,
    onSecondary = Color.White,

    secondaryContainer = GoldContainer,
    onSecondaryContainer = Gold,

    tertiary = Sky,

    background = BackgroundLight,
    onBackground = TextPrimaryLight,

    surface = SurfaceLight,
    onSurface = TextPrimaryLight,

    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = TextSecondaryLight,

    outline = BorderLight,

    error = Error,
    onError = Color.White
)

private val DarkColors = darkColorScheme(

    primary = Emerald,

    onPrimary = Color.White,

    primaryContainer = EmeraldDark,

    onPrimaryContainer = Color.White,

    secondary = Gold,

    onSecondary = Color.Black,

    tertiary = Sky,

    background = BackgroundDark,

    onBackground = TextPrimaryDark,

    surface = SurfaceDark,

    onSurface = TextPrimaryDark,

    surfaceVariant = SurfaceVariantDark,

    onSurfaceVariant = TextSecondaryDark,

    outline = BorderDark,

    error = Error,

    onError = Color.White
)

@Composable
fun NoorMuslimCompanionTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = RamadanTypography,
        content = content
    )
}