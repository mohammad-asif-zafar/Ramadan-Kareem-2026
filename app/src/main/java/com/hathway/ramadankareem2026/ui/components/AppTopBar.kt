@file:OptIn(ExperimentalMaterial3Api::class)

package com.hathway.ramadankareem2026.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun AppTopBar(
    titleRes: Int, navController: NavController, showBack: Boolean = true
) {
    CenterAlignedTopAppBar(
        title = {
        Text(
            text = stringResource(titleRes), style = MaterialTheme.typography.titleMedium
        )
    }, navigationIcon = {
        if (showBack) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack, contentDescription = "Back"
                )
            }
        }
    }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = Color.Transparent
    )
    )
}
