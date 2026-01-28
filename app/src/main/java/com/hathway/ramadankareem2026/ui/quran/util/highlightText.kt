package com.hathway.ramadankareem2026.ui.quran.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString

fun highlightText(
    fullText: String, query: String, highlightColor: Color
): AnnotatedString {
    if (query.isBlank()) return AnnotatedString(fullText)

    val startIndex = fullText.indexOf(query, ignoreCase = true)
    if (startIndex < 0) return AnnotatedString(fullText)

    return buildAnnotatedString {
        append(fullText)

        addStyle(
            style = SpanStyle(
                background = highlightColor.copy(alpha = 0.3f)
            ), start = startIndex, end = startIndex + query.length
        )
    }
}
