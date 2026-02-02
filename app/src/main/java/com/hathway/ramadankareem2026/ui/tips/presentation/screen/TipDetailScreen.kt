package com.hathway.ramadankareem2026.ui.tips.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.theme.RamadanGold
import com.hathway.ramadankareem2026.ui.tips.presentation.components.TipDetailCard
import com.hathway.ramadankareem2026.ui.tips.presentation.viewmodel.TipsViewModel
import com.hathway.ramadankareem2026.ui.tips.presentation.viewmodel.TipsViewModelFactory

@Composable
fun TipDetailScreen(
    tipId: Int,
    onBack: () -> Unit,
    viewModel: TipsViewModel = viewModel(factory = TipsViewModelFactory())
) {
    val tip = remember(tipId) { viewModel.getTipById(tipId) }
    if (tip == null) {
        // Show error state
        Scaffold(
            topBar = {
                RamadanToolbar(
                    title = "Tip Not Found",
                    showBack = true,
                    onBackClick = onBack,
                    backgroundColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tip not found",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        return
    }
    
    Scaffold(
        topBar = {
            RamadanToolbar(
                title = tip.category.displayName,
                showBack = true,
                onBackClick = onBack,
                backgroundColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            TipDetailCard(tip = tip)
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Additional actions or related tips could go here
            if (tip.isDaily) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = RamadanGold.copy(alpha = 0.05f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Daily Reminder",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = RamadanGold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "This tip changes daily based on calendar date. Check back tomorrow for a new Ramadan tip!",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
