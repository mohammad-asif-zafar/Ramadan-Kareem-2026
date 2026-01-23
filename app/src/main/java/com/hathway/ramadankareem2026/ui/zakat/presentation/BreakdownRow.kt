package com.hathway.ramadankareem2026.ui.zakat.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import java.text.NumberFormat
import java.util.Locale

@Composable
fun BreakdownRow(
    label: String,
    value: Double,
    suffix: String = "",
    highlight: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = "${"%.2f".format(value)} $suffix",
            style = if (highlight)
                MaterialTheme.typography.titleMedium
            else
                MaterialTheme.typography.bodyMedium,
            color = if (highlight)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onSurface
        )
    }
}



/** Formats currency consistently */
private fun formatCurrency(amount: Double): String {
    return NumberFormat
        .getCurrencyInstance(Locale.getDefault())
        .format(amount)
}
