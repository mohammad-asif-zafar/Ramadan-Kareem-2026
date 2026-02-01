package com.hathway.ramadankareem2026.ui.quran.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.navigation.Routes
import com.hathway.ramadankareem2026.ui.quran.domain.model.Surah
import com.hathway.ramadankareem2026.ui.quran.presentation.viewmodel.QuranBookmarkCountViewModel
import com.hathway.ramadankareem2026.ui.theme.RamadanGold
import com.hathway.ramadankareem2026.ui.theme.RamadanGreen

@Composable
fun QuranSurahListScreen(
    viewModel: QuranViewModel,
    quranBookmarkCountViewModel: QuranBookmarkCountViewModel,
    onBack: () -> Unit,
    onSurahClick: (Surah) -> Unit,
    navController: NavController
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        if (state.surahList.isEmpty() && !state.isLoading) {
            viewModel.loadSurahs()
        }
    }

    var searchQuery by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val filteredSurahs = state.surahList.filter {
        searchQuery.isBlank() ||
        it.name.contains(searchQuery, ignoreCase = true) ||
        it.englishName.contains(searchQuery, ignoreCase = true) ||
        it.englishNameTranslation.contains(searchQuery, ignoreCase = true)
    }

    val bookmarkCount by quranBookmarkCountViewModel.quranBookmarkCount.collectAsStateWithLifecycle(initialValue = 0)
    
    // Set up callback for immediate Quran badge updates
    LaunchedEffect(Unit) {
        // Note: QuranViewModel doesn't have setBookmarkCountChangedCallback like other ViewModels
        // We'll refresh the count periodically or when needed
    }

    Scaffold(
        topBar = {
            RamadanToolbar(
                title = "Quran",
                showBack = true,
                onBackClick = onBack,
                // Bookmarks
                rightIcon1 = R.drawable.ic_saved,
                rightIcon1Badge = bookmarkCount,
                onRightIcon1Click = {
                    // Navigate to Quran bookmarks list
                    navController.navigate(Routes.QURAN_BOOKMARKS)
                }
            )
        }
    ) { padding ->
        when {
            state.isLoading && state.surahList.isEmpty() -> {
                QuranSurahListSkeleton(modifier = Modifier.padding(padding))
            }

            state.errorMessage != null && state.surahList.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.errorMessage ?: "Failed to load",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    // Search field
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        placeholder = { Text("Search Surahs...") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(
                            onSearch = { keyboardController?.hide() }
                        )
                    )

                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(filteredSurahs, key = { it.id }) { surah ->
                            SurahCard(
                                surah = surah,
                                onClick = { onSurahClick(surah) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SurahCard(
    surah: Surah,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(20.dp),
        tonalElevation = 1.dp,
        shadowElevation = 2.dp,
        color = MaterialTheme.colorScheme.surface,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Surah number badge
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        color = RamadanGreen.copy(alpha = 0.12f), shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = surah.id.toString(),
                    style = MaterialTheme.typography.labelMedium,
                    color = RamadanGreen
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                // Arabic name (GOLD)
                Text(
                    text = surah.name,
                    style = MaterialTheme.typography.headlineSmall,
                    color = RamadanGold
                )

                Spacer(modifier = Modifier.height(4.dp))

                // English name and details
                Text(
                    text = "${surah.englishName} • ${surah.numberOfAyahs} ayahs • ${surah.revelationType}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
