package com.hathway.ramadankareem2026.ui.dua.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.ui.dua.model.DuaItem


@Composable
fun RamadanDuaHorizontal(
    duas: List<DuaItem>,
    onClick: (DuaItem) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(duas) { dua ->
            RamadanDuaCard(
                dua = dua,
                onClick = { onClick(dua) }
            )
        }
    }
}
