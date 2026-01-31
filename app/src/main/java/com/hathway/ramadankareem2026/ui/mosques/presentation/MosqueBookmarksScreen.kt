package com.hathway.ramadankareem2026.ui.mosques.presentation

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
import com.hathway.ramadankareem2026.data.local.database.BookmarkEntity
import com.hathway.ramadankareem2026.ui.bookmarks.presentation.BookmarkItem
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.mosques.presentation.viewmodel.MosqueBookmarksViewModel

// Unique ViewModel factory key to ensure complete isolation
private val MOSQUE_BOOKMARKS_VIEWMODEL_KEY = "MosqueBookmarksViewModel_Unique"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MosqueBookmarksScreen(
    onBack: () -> Unit,
    navController: NavController,
    viewModel: MosqueBookmarksViewModel = viewModel(key = MOSQUE_BOOKMARKS_VIEWMODEL_KEY)
) {
    val bookmarks by viewModel.mosqueBookmarks.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            RamadanToolbar(
                title = "Mosque Bookmarks",
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
                        // Navigate to nearby mosque screen
                        navController.navigate("mosques")
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
                            text = "No Mosque bookmarks yet",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
