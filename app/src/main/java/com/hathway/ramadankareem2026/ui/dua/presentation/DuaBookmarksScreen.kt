package com.hathway.ramadankareem2026.ui.dua.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.data.local.database.BookmarkEntity
import com.hathway.ramadankareem2026.ui.bookmarks.presentation.BookmarkItem
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.dua.presentation.viewmodel.DuaBookmarksViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DuaBookmarksScreen(
    onBack: () -> Unit,
    navController: NavController,
    viewModel: DuaBookmarksViewModel = viewModel()
) {
    val bookmarks by viewModel.duaBookmarks.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            RamadanToolbar(
                title = "Dua Bookmarks",
                showBack = true,
                onBackClick = onBack
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(bookmarks) { bookmark ->
                BookmarkItem(
                    bookmark = bookmark,
                    onClick = {
                        // Navigate to Dua detail screen
                        navController.navigate("dua_detail/${bookmark.itemId}")
                    }
                )
            }

            if (bookmarks.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No Dua bookmarks yet",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
