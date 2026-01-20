package com.hathway.ramadankareem2026.ui.dua

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.dua.components.DuaCategoriesGrid
import com.hathway.ramadankareem2026.ui.dua.components.RamadanDuaHorizontal
import com.hathway.ramadankareem2026.ui.dua.data.DuaCategoryData
import com.hathway.ramadankareem2026.ui.dua.viewmodel.DuaViewModel
import com.hathway.ramadankareem2026.ui.home.components.SectionTitle
import com.hathway.ramadankareem2026.ui.navigation.Routes
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.hathway.ramadankareem2026.ui.theme.RamadanKareemTheme

/**
 * 🌙 Dua Main Screen
 *
 * UI Structure:
 * 1️⃣ Toolbar
 * 2️⃣ "Ramadan Collections" title
 * 3️⃣ Ramadan Duas (horizontal list)
 * 4️⃣ "All Duʿāʾs" title
 * 5️⃣ Dua Categories (2×2 grid)
 *
 * All content scrolls vertically (single LazyColumn)
 */
@Composable
fun DuaScreen(
    navController: NavController,
    onBack: () -> Unit,
    onCalendarClick: () -> Unit,
    viewModel: DuaViewModel = viewModel()
) {
    Scaffold(

        /* 🔝 Top App Bar */
        topBar = {
            RamadanToolbar(
                title = stringResource(R.string.feature_dua),     // ✅ string resource ID
                showBack = true, onBackClick = onBack,

                // 📅 Calendar icon
                rightIcon1 = R.drawable.bell, onRightIcon1Click = onCalendarClick,

                // ⚙️ Settings icon
                rightIcon2 =  R.drawable.bell, onRightIcon2Click = {
                    navController.navigate(Routes.QIBLA_SETTINGS)
                })
        }

    ) { padding ->

        /* 📜 Main scrollable content */
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            /* 2️⃣ Ramadan Collections label */
            item {
                SectionTitle("Ramadan Collections")
            }

            /* 3️⃣ Ramadan Duas – horizontal cards */
            item {
                RamadanDuaHorizontal(
                    duas = viewModel.ramadanDuas, onClick = { dua ->
                        navController.navigate("dua_detail/${dua.id}")
                    })
            }

            /* 4️⃣ All Duʿāʾs label */
            item {
                SectionTitle("All Duʿāʾs")
            }

            /* 5️⃣ Dua Categories – 2×2 grid */
            item {
                DuaCategoriesGrid(
                    categories = DuaCategoryData.list, onClick = { category ->
                        navController.navigate("dua_category/${category.id}")
                    })
            }
        }
    }
}


@Preview(
    name = "Dua Screen – Main", device = Devices.PIXEL_6, showBackground = true
)
@Composable
fun DuaScreenPreview() {
    RamadanKareemTheme {
        DuaScreen(navController = rememberNavController(), onBack = {}, onCalendarClick = {})
    }
}
