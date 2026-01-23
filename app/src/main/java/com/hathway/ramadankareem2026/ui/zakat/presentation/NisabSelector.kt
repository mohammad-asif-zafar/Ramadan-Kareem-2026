package com.hathway.ramadankareem2026.ui.zakat.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.ui.zakat.domain.model.NisabType

@Composable
fun NisabSelector(
    selected: NisabType,
    onSelect: (NisabType) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        NisabType.values().forEach {
            FilterChip(
                selected = selected == it,
                onClick = { onSelect(it) },
                label = { Text(it.name) }
            )
        }
    }
}

