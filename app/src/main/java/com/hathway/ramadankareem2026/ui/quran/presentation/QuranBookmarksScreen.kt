package com.hathway.ramadankareem2026.ui.quran.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import com.hathway.ramadankareem2026.ui.quran.presentation.viewmodel.QuranBookmarksViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuranBookmarksScreen(
    onBack: () -> Unit,
    navController: NavController,
    viewModel: QuranBookmarksViewModel = viewModel()
) {
    val bookmarks by viewModel.quranBookmarks.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            RamadanToolbar(
                title = "Quran Bookmarks",
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
                        // Navigate to Quran content based on bookmark data
                        // This would depend on how Quran content is structured
                        navController.navigate("quran_surah/${bookmark.itemId}")
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
                            text = "No Quran bookmarks yet",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
