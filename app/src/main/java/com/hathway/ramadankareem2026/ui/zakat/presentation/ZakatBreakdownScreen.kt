package com.hathway.ramadankareem2026.ui.zakat.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.zakat.domain.model.NisabType
import com.hathway.ramadankareem2026.ui.zakat.domain.model.ZakatAssets
import com.hathway.ramadankareem2026.ui.zakat.domain.model.ZakatResult


@Composable
fun ZakatBreakdownScreen(
    result: ZakatResult,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            RamadanToolbar("Zakat Breakdown", true, onBack)
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text("Assets")
            Text("Gold: ${result.assets.gold}")
            Text("Silver: ${result.assets.silver}")
            Text("Cash: ${result.assets.cash}")
            Text("Debts: ${result.assets.debts}")

            HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

            Text("Nisab (${result.nisabType}): ${result.nisabValue}")
            Text("Zakat Payable: ${result.zakatAmount}")
        }
    }
}


