package com.hathway.ramadankareem2026.core.util

import kotlinx.coroutines.delay
import androidx.compose.runtime.*

@Composable
fun rememberLiveRemainingMinutes(
    initialMinutes: Int,
    isActive: Boolean
): Int {
    var minutes by remember { mutableStateOf(initialMinutes) }

    LaunchedEffect(isActive) {
        if (!isActive) return@LaunchedEffect

        while (minutes > 0) {
            delay(60_000) // 1 minute
            minutes -= 1
        }
    }

    return minutes
}
