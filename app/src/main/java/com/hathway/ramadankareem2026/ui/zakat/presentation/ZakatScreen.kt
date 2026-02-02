package com.hathway.ramadankareem2026.ui.zakat.presentation

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.theme.RamadanGold
import com.hathway.ramadankareem2026.ui.theme.RamadanGreen
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
    onBack: () -> Unit,
    navController: NavController,
    onAiSearch: () -> Unit = {},
    zakatHistoryCount: Int = 0,
    onCalculatorIconClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    // Auto-scroll to breakdown when result is available
    LaunchedEffect(state.result) {
        state.result?.let {
            coroutineScope.launch {
                // Smooth, slow scroll to breakdown (1200ms duration with easing)
                scrollState.animateScrollTo(
                    value = scrollState.maxValue, animationSpec = tween(
                        durationMillis = 1200,
                        easing = androidx.compose.animation.core.FastOutSlowInEasing
                    )
                )
            }
        }
    }

    Scaffold(
        topBar = {
            RamadanToolbar(
                title = stringResource(R.string.zakat),
                showBack = true,
                onBackClick = onBack,
                rightIcon1 = R.drawable.ic_calculator,
                rightIcon1Badge = if (zakatHistoryCount > 0) zakatHistoryCount else null,
                onRightIcon1Click = onCalculatorIconClick,
                /*rightIcon2 = R.drawable.ic_ai_search,
                onRightIcon2Click = onAiSearch*/
            )
        }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(24.dp), verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Header with History Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.zakat_calculator_title),
                        style = MaterialTheme.typography.headlineSmall,
                        color = RamadanGold,
                        fontWeight = FontWeight.Bold
                    )
                    
                    // Currency and Country Info
                    if (state.country.isNotEmpty()) {
                        Text(
                            text = "${state.currency.symbol} ${state.currency.name} • ${state.country}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                IconButton(
                    onClick = onCalculatorIconClick, modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.surfaceVariant, shape = CircleShape
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_zakat_history),
                        contentDescription = stringResource(R.string.zakat_history_description),
                        tint = RamadanGold,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Text(
                text = stringResource(R.string.calculate_your_zakat),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Assets Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.assets),
                    style = MaterialTheme.typography.titleMedium,
                    color = RamadanGold
                )
                Surface(
                    shape = RoundedCornerShape(8.dp), color = RamadanGold.copy(alpha = 0.1f)
                ) {
                    Text(
                        text = "💰",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 16.sp
                    )
                }
            }

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 1.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ZakatMoneyField(stringResource(R.string.gold_value), state.gold, onGold, KeyboardType.Number, state.currency.symbol)
                    ZakatMoneyField(stringResource(R.string.silver_value), state.silver, onSilver, KeyboardType.Number, state.currency.symbol)
                    ZakatMoneyField(stringResource(R.string.cash_savings), state.cash, onCash, KeyboardType.Number, state.currency.symbol)
                }
            }

            // Liabilities Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.liabilities),
                    style = MaterialTheme.typography.titleMedium,
                    color = RamadanGold
                )
                Surface(
                    shape = RoundedCornerShape(8.dp), color = RamadanGold.copy(alpha = 0.1f)
                ) {
                    Text(
                        text = "📋",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 16.sp
                    )
                }
            }

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 1.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ZakatMoneyField(stringResource(R.string.debts_loans), state.debts, onDebts, KeyboardType.Number, state.currency.symbol)
                }
            }

            // Calculate Button
            Button(
                onClick = onCalculate,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = RamadanGreen, contentColor = Color.White
                )
            ) {
                Text(stringResource(R.string.calculate_zakat), fontWeight = FontWeight.Medium)
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
                            text = stringResource(R.string.zakat_breakdown),
                            style = MaterialTheme.typography.titleMedium,
                            color = RamadanGold
                        )

                        HorizontalDivider()

                        // Assets Summary
                        Text(
                            text = stringResource(R.string.assets_colon),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            "${stringResource(R.string.gold_colon)} ${state.currency.symbol}${result.assets.gold}",
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            "${stringResource(R.string.silver_colon)} ${state.currency.symbol}${result.assets.silver}",
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            "${stringResource(R.string.cash_colon)} ${state.currency.symbol}${result.assets.cash}",
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = stringResource(R.string.liabilities_colon),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            "${stringResource(R.string.debts_colon)} ${state.currency.symbol}${result.assets.debts}",
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        HorizontalDivider()

                        // Net Worth
                        val netWorth =
                            result.assets.gold + result.assets.silver + result.assets.cash - result.assets.debts
                        Text(
                            text = "${stringResource(R.string.net_wealth)} ${state.currency.symbol}$netWorth",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "${stringResource(R.string.nisab_threshold)} ${state.currency.symbol}${result.nisabValue}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        HorizontalDivider()

                        // Final Zakat
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = RamadanGold.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = stringResource(R.string.total_zakat_payable),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "${state.currency.symbol}${result.zakatAmount}",
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = RamadanGold,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = stringResource(R.string.zakat_percentage),
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

@Preview(showBackground = true)
@Composable
fun ZakatScreenPreview() {
    ZakatScreen(
        state = ZakatUiState(
            gold = "20000", silver = "5000", cash = "10000", debts = "3000"
        ),
        onGold = {},
        onSilver = {},
        onCash = {},
        onDebts = {},
        onNisab = {},
        onCalculate = {},
        onViewBreakdown = {},
        onBack = {},
        navController = rememberNavController(),
        onCalculatorIconClick = {}
    )
}



