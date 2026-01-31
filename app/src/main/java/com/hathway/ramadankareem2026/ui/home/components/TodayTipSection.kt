package com.hathway.ramadankareem2026.ui.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.ramadankareem2026.R

/**
 * Displays a daily inspirational tip.
 *
 * Used on:
 * - Home screen
 * - Ramadan dashboard
 *
 * Purpose:
 * - Provide a short, meaningful reminder
 * - Visually separated using a Card
 */
@Composable
fun TodayTipSection() {

    /**
     * Column arranges the title and card vertically.
     */
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {

        // Section header
        SectionTitle(stringResource(R.string.todays_tip))

        Spacer(modifier = Modifier.height(8.dp))

        /**
         * Card used to visually highlight the tip content.
         */
        Card(
            shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()
        ) {

            // Tip text
            Text(
                text = "Remember Allah often and keep your intentions pure today.",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

    }
}

@Preview(showBackground = true)
@Composable
fun TodayTipSectionPreview() {
    MaterialTheme {
        TodayTipSection()
    }
}


/*
 Random daily tips
 Arabic + English tips
 Gradient card
 Preview with long text edge cases*/
