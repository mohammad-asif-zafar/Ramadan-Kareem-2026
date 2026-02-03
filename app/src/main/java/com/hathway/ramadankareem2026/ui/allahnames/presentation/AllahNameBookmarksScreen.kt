package com.hathway.ramadankareem2026.ui.allahnames.presentation

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.ui.allahnames.presentation.viewmodel.AllahNameBookmarksViewModel
import com.hathway.ramadankareem2026.ui.bookmarks.presentation.BookmarkItem
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar

// Unique ViewModel factory key to ensure complete isolation
private const val ALLAH_NAME_BOOKMARKS_VIEWMODEL_KEY = "AllahNameBookmarksViewModel_Unique"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllahNameBookmarksScreen(
    onBack: () -> Unit,
    navController: NavController,
    viewModel: AllahNameBookmarksViewModel = viewModel(key = ALLAH_NAME_BOOKMARKS_VIEWMODEL_KEY)
) {
    val bookmarks by viewModel.allahNameBookmarks.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            RamadanToolbar(
                title = "Allah Name Bookmarks",
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
                        // Navigate to Allah Name detail screen
                        navController.navigate("allah_name_detail/${bookmark.itemId}")
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
                            text = "No Allah Name bookmarks yet",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
