package com.hathway.ramadankareem2026.ui.qibla.logic

fun shouldVibrate(
    isAligned: Boolean, hasVibrated: Boolean, vibrationEnabled: Boolean
): Boolean {
    return isAligned && !hasVibrated && vibrationEnabled
}