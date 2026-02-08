package com.hathway.ramadankareem2026.ui.dua

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.dua.components.DuaCategoriesGrid
import com.hathway.ramadankareem2026.ui.dua.components.RamadanDuaHorizontal
import com.hathway.ramadankareem2026.ui.dua.data.DuaCategoryData
import com.hathway.ramadankareem2026.ui.dua.presentation.viewmodel.DuaBookmarkCountViewModel
import com.hathway.ramadankareem2026.ui.dua.viewmodel.DuaBookmarkViewModel
import com.hathway.ramadankareem2026.ui.dua.viewmodel.DuaViewModel
import com.hathway.ramadankareem2026.ui.home.components.SectionTitle
import com.hathway.ramadankareem2026.ui.navigation.Routes
import com.hathway.ramadankareem2026.ui.theme.RamadanKareemTheme

/**
 *  Dua Main Screen
 *
 * UI Structure:
 * Toolbar
 * "Ramadan Collections" title
 * Ramadan Duas (horizontal list)
 * "All Duʿāʾs" title
 * Dua Categories (2×2 grid)
 *
 * All content scrolls vertically (single LazyColumn)
 */
@Composable
fun DuaScreen(
    navController: NavController,
    onBack: () -> Unit,
    viewModel: DuaViewModel = viewModel(),
    bookmarkViewModel: DuaBookmarkViewModel = viewModel(),
    duaBookmarkCountViewModel: DuaBookmarkCountViewModel = viewModel()
) {
    val bookmarkCount by duaBookmarkCountViewModel.duaBookmarkCount.collectAsStateWithLifecycle(initialValue = 0)
    
    // Set up callback for immediate dua badge updates
    LaunchedEffect(Unit) {
        bookmarkViewModel.setBookmarkCountChangedCallback { delta ->
            duaBookmarkCountViewModel.updateDuaBookmarkCountImmediate(delta)
        }
    }

    Scaffold(

        topBar = {
            RamadanToolbar(
                title = stringResource(R.string.feature_dua),
                showBack = true, onBackClick = onBack, onRightIcon1Click = {
                    navController.navigate(Routes.DUA_BOOKMARKS)
                }, rightIcon1Badge = bookmarkCount,
                // Bookmarks
                rightIcon1 = R.drawable.ic_saved,
)
        }

    ) { padding ->

        /*  Main scrollable content */
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
            }

            item {
                SectionTitle(stringResource(R.string.ramadan_collections))
            }

            /*  Ramadan Duas – horizontal cards */
            item {
                RamadanDuaHorizontal(
                    duas = viewModel.ramadanDuas, onClick = { dua ->
                        navController.navigate("dua_detail/${dua.id}")
                    })
            }

            /*  All Duʿāʾs label */
            item {
                SectionTitle(stringResource(R.string.all)+" "+ stringResource(R.string.dua))
            }

            /*  Dua Categories – 2×2 grid */
            item {
                DuaCategoriesGrid(
                    categories = DuaCategoryData.list, onClick = { category ->
                        navController.navigate("dua_category/${category.id}")
                    })
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
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
        DuaScreen(
            navController = rememberNavController(), 
            onBack = {}
        )
    }
}
