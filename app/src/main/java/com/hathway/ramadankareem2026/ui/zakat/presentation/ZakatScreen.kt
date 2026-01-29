package com.hathway.ramadankareem2026.ui.zakat.presentation

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.zakat.domain.model.NisabType
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZakatScreen(
    state: ZakatUiState,
    onGold: (String) -> Unit,
    onSilver: (String) -> Unit,
    onCash: (String) -> Unit,
    onDebts: (String) -> Unit,
    onNisab: (NisabType) -> Unit,
    onCalculate: () -> Unit,
    onViewBreakdown: () -> Unit,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val density = LocalDensity.current
    
    // Auto-scroll to breakdown when result is available
    LaunchedEffect(state.result) {
        state.result?.let {
            coroutineScope.launch {
                // Smooth, slow scroll to breakdown (1200ms duration with easing)
                scrollState.animateScrollTo(
                    value = scrollState.maxValue,
                    animationSpec = tween(
                        durationMillis = 1200,
                        easing = androidx.compose.animation.core.FastOutSlowInEasing
                    )
                )
            }
        }
    }
    
    Scaffold(
        topBar = {
            RamadanToolbar("Zakat Calculator", true, onBack)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Simple Header
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 1.dp
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Zakat Calculator",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Calculate your Zakat (2.5% of wealth)",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            // Assets Section
            Text(
                text = "Assets",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 1.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ZakatMoneyField("Gold Value", state.gold, onGold)
                    ZakatMoneyField("Silver Value", state.silver, onSilver)
                    ZakatMoneyField("Cash & Savings", state.cash, onCash)
                }
            }
            
            // Liabilities Section
            Text(
                text = "Liabilities",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 1.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ZakatMoneyField("Debts / Loans", state.debts, onDebts)
                }
            }
            
            // Calculate Button
            Button(
                onClick = onCalculate,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Calculate Zakat")
            }
            
            // Breakdown Section (shown after calculation)
            state.result?.let { result ->
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 2.dp
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Zakat Breakdown",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        
                        HorizontalDivider()
                        
                        // Assets Summary
                        Text(
                            text = "Assets:",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text("Gold: ${result.assets.gold}", color = MaterialTheme.colorScheme.onSurface)
                        Text("Silver: ${result.assets.silver}", color = MaterialTheme.colorScheme.onSurface)
                        Text("Cash: ${result.assets.cash}", color = MaterialTheme.colorScheme.onSurface)
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "Liabilities:",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text("Debts: ${result.assets.debts}", color = MaterialTheme.colorScheme.onSurface)
                        
                        HorizontalDivider()
                        
                        // Net Worth
                        val netWorth = result.assets.gold + result.assets.silver + result.assets.cash - result.assets.debts
                        Text(
                            text = "Net Wealth: $netWorth",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Nisab Threshold: ${result.nisabValue}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        
                        HorizontalDivider()
                        
                        // Final Zakat
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Total Zakat Payable",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "${result.zakatAmount}",
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "(2.5% of eligible wealth)",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}



