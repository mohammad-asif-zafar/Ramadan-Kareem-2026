package com.hathway.ramadankareem2026.ui.zakat.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.zakat.domain.model.NisabType

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
    Scaffold(
        topBar = {
            RamadanToolbar("Zakat Calculator", true, onBack)
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text("Select Nisab Type")
            NisabSelector(state.selectedNisab, onNisab)

            Text("Assets")
            ZakatMoneyField("Gold Value", state.gold, onGold)
            ZakatMoneyField("Silver Value", state.silver, onSilver)
            ZakatMoneyField("Cash & Savings", state.cash, onCash)

            Divider()

            Text("Liabilities")
            ZakatMoneyField("Debts / Loans", state.debts, onDebts)

            Button(onClick = onCalculate, modifier = Modifier.fillMaxWidth()) {
                Text("Calculate Zakat")
            }

            state.result?.let {
                Button(onClick = onViewBreakdown, modifier = Modifier.fillMaxWidth()) {
                    Text("View Breakdown")
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ZakatPreview() {
    ZakatScreen(
        state = ZakatUiState(
            gold = "20000",
            silver = "5000",
            cash = "10000",
            debts = "3000"
        ),
        onGold = {},
        onSilver = {},
        onCash = {},
        onDebts = {},
        onNisab = {},
        onCalculate = {},
        onViewBreakdown = {},
        onBack = {}
    )
}



