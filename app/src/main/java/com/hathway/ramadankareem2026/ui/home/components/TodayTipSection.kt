package com.hathway.ramadankareem2026.ui.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.ramadankareem2026.ui.tips.presentation.components.DailyTipCard
import com.hathway.ramadankareem2026.ui.tips.presentation.viewmodel.TipsViewModel
import com.hathway.ramadankareem2026.ui.tips.presentation.viewmodel.TipsViewModelFactory

/**
 * Displays a daily inspirational tip using MVVM architecture.
 *
 * Used on:
 * - Home screen
 * - Ramadan dashboard
 *
 * Purpose:
 * - Provide a short, meaningful reminder
 * - Visually separated using a Card
 * - Real data from TipsRepository
 */
@Composable
fun TodayTipSection(
    onTipClick: (Int) -> Unit = {},
    viewModel: TipsViewModel = viewModel(factory = TipsViewModelFactory())
) {
    val dailyTip by viewModel.dailyTip.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        // Section header
        SectionTitle("Today's Tip")

        Spacer(modifier = Modifier.height(8.dp))

        // Daily tip card with real data
        DailyTipCard(
            tip = dailyTip,
            onClick = { onTipClick(dailyTip.id) }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun TodayTipSectionPreview() {
    MaterialTheme {
        // Note: This preview won't show real data without proper ViewModel setup
        // In a real app, you'd create a mock ViewModel for preview
        Column {
            SectionTitle("Today's Tip")
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                shape = RoundedCornerShape(16.dp), 
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Preview: Real daily tip will appear here with MVVM architecture",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
