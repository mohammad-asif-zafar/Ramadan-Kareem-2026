package com.hathway.ramadankareem2026.ui.dua

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.components.AppTopBar

@Composable
fun DuaScreen(titleScreenName: String, navController: NavController) {
    Scaffold(
        topBar = {
            AppTopBar(
                titleRes = R.string.feature_dua, navController = navController
            )
        }) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding), contentAlignment = Alignment.Center
        ) {
            Text("Dua Screen Content")
        }
    }
}
