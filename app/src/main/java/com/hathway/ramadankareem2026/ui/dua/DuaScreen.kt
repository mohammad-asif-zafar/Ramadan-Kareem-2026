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

@Composable
fun DuaScreen(
    navController: NavController,
    onBack: () -> Unit,
    onCalendarClick: () -> Unit,
    viewModel: DuaViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            RamadanToolbar(
                title = R.string.feature_dua.toString(),
                showBack = true,
                onBackClick = onBack,
                rightIcon1 = Icons.Default.CalendarMonth,
                onRightIcon1Click = onCalendarClick,
                rightIcon2 = Icons.Default.Settings,
                onRightIcon2Click = {
                    navController.navigate(Routes.QIBLA_SETTINGS)
                })
        }) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            /* 2️⃣ Ramadan Collections */
            item {
                SectionTitle("Ramadan Collections")
            }

            /* 3️⃣ Ramadan Duas – Horizontal */
            item {
                RamadanDuaHorizontal(
                    duas = viewModel.ramadanDuas, onClick = { dua ->
                        navController.navigate("dua_detail/${dua.id}")
                    })
            }

            /* 4️⃣ All Duas */
            item {
                SectionTitle("All Duʿāʾs")
            }

            /* 5️⃣ Dua Categories – 2×2 Grid */
            item {
                DuaCategoriesGrid(
                    categories = DuaCategoryData.list, onClick = { category ->
                        navController.navigate("dua_category/${category.id}")
                    })
            }
        }
    }
}


