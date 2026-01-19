package com.hathway.ramadankareem2026.ui.dua.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.dua.data.DuaRepository
import com.hathway.ramadankareem2026.ui.navigation.Routes

@Composable
fun DuaCategoryScreen(
    categoryId: String,
    navController: NavController
) {
    val repository = remember { DuaRepository() }
    val duas = remember(categoryId) {
        repository.getDuasByCategory(categoryId)
    }

    Scaffold(
        topBar = {
            RamadanToolbar(
                title = "Duʿās",
                showBack = true,
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(duas) { dua ->
                DuaListItem(
                    title = dua.title,
                    onClick = {
                        navController.navigate(
                            "${Routes.DUA_DETAIL}/${dua.id}"
                        )
                    }
                )
            }
        }
    }
}
