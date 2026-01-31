package com.hathway.ramadankareem2026.ui.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/**
 * Displays a standard section title used across the app.
 *
 * Usage:
 * - Section headers on Home screen
 * - Prayer Times section
 * - Duʿā sections
 *
 * Design intent:
 * - Consistent typography
 * - Theme-aware styling
 */
@Composable
fun SectionTitle(
    title: String
) {
    Text(
        text = title, style = MaterialTheme.typography.titleMedium
    )
}

@Preview(showBackground = true)
@Composable
fun SectionTitlePreview() {
    MaterialTheme {
        SectionTitle("Prayer Times")
    }
}

@Composable
fun SectionTitle(
    title: String, modifier: Modifier = Modifier
) {
    Text(
        text = title, modifier = modifier, style = MaterialTheme.typography.titleMedium
    )
}

@Composable
fun SectionTitle(
    title: String, subtitle: String? = null, modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            text = title, style = MaterialTheme.typography.titleMedium
        )

        if (!subtitle.isNullOrBlank()) {
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}/*
 Design system setup

 Snapshot testing typography

 Dark mode previews

 Adaptive typography for tablets*/
