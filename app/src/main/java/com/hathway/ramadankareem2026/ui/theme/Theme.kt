package com.hathway.ramadankareem2026.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = RamadanGreen,
    secondary = RamadanGold,
    background = LightBackground,
    surface = LightSurface,

    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = LightTextPrimary,
    onSurface = LightTextPrimary,

    error = ErrorRed
)

private val DarkColorScheme = darkColorScheme(
    primary = RamadanGreen,
    secondary = RamadanGold,
    background = DarkBackground,
    surface = DarkSurface,

    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = DarkTextPrimary,
    onSurface = DarkTextPrimary,

    error = ErrorRed
)

@Composable
fun RamadanKareemTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = RamadanTypography,
        content = content
    )
}