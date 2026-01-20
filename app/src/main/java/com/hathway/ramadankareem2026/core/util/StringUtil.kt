package com.hathway.ramadankareem2026.core.util

fun String.capitalizeFirst(): String = replaceFirstChar { char ->
    if (char.isLowerCase()) char.titlecase() else char.toString()
}