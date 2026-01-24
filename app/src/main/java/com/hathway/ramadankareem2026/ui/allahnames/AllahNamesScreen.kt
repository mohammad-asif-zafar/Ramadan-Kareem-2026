package com.hathway.ramadankareem2026.ui.allahnames

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.ui.allahnames.components.AllahNameCard
import com.hathway.ramadankareem2026.ui.allahnames.domain.model.AllahName
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar

@Composable
fun AllahNamesScreen(
    names: List<AllahName>, onBack: () -> Unit, onNameClick: (AllahName) -> Unit
) {
    Scaffold(
        topBar = {
            RamadanToolbar(
                title = "Names of Allah", showBack = true, onBackClick = onBack
            )
        }, containerColor = MaterialTheme.colorScheme.background
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            items(names) { name ->
                AllahNameCard(
                    name = name, onClick = { onNameClick(name) })
            }
        }
    }
}
