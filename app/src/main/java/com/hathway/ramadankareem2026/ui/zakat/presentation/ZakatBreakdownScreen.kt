package com.hathway.ramadankareem2026.ui.zakat.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.R
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
            RamadanToolbar(stringResource(R.string.zakat_breakdown), true, onBack)
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(stringResource(R.string.assets))
            Text("${stringResource(R.string.gold_colon)} ${result.assets.gold}")
            Text("${stringResource(R.string.silver_colon)} ${result.assets.silver}")
            Text("${stringResource(R.string.cash_colon)} ${result.assets.cash}")
            Text("${stringResource(R.string.debts_colon)} ${result.assets.debts}")

            HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

            Text("${stringResource(R.string.nisab)} (${result.nisabType}): ${result.nisabValue}")
            Text("${stringResource(R.string.zakat_payable)}: ${result.zakatAmount}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ZakatBreakdownScreenPreview() {
    ZakatBreakdownScreen(
        result = ZakatResult(
            assets = ZakatAssets(
                gold = 20000.0,
                silver = 5000.0,
                cash = 10000.0,
                debts = 3000.0
            ),
            nisabValue = 45000.0,
            zakatAmount = 800.0,
            isEligible = true,
            nisabType = NisabType.GOLD
        ),
        onBack = {}
    )
}


