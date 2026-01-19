package com.hathway.ramadankareem2026.ui.dua.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.ui.dua.model.DuaCategory

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DuaCategoriesGrid(
    categories: List<DuaCategory>,
    onClick: (DuaCategory) -> Unit
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        maxItemsInEachRow = 2,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        categories.forEach { category ->
            DuaCategoryCard(
                modifier = Modifier.weight(1f),
                category = category,
                onClick = { onClick(category) }
            )
        }
    }
}

