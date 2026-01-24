package com.hathway.ramadankareem2026.ui.dua.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.dua.model.DuaItem

@Composable
fun DuaListScreen(
    title: String, duas: List<DuaItem>, onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            RamadanToolbar(
                title = "name", showBack = true, onBackClick = onBack
            )
        }) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
            }
            items(duas) { dua ->
                DuaItemCard(dua = dua)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DuaListScreenPreview() {
    DuaListScreen(
        title = "Duʿāʾs", duas = emptyList(), onBack = {})
}
