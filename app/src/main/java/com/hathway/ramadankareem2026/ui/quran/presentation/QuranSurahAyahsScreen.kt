package com.hathway.ramadankareem2026.ui.quran.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.quran.components.AyahItem
import com.hathway.ramadankareem2026.ui.quran.components.SurahHeaderCard

@Composable
fun QuranSurahAyahsScreen(
    viewModel: QuranViewModel,
    surahId: Int,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val bookmarkedAyahs by viewModel.bookmarkedAyahs.collectAsState()
    val lastReadAyah by viewModel.lastReadAyah.collectAsState()

    val listState = rememberLazyListState()

    LaunchedEffect(surahId, state.surahList) {
        if (surahId > 0) {
            if (state.surahList.isEmpty() && !state.isLoading) {
                viewModel.loadSurahs()
            }
            viewModel.loadAyahsById(surahId)
        }
    }

    // Resume last read within current surah
    LaunchedEffect(state.ayahs, lastReadAyah, state.selectedSurah?.id) {
        val currentSurahId = state.selectedSurah?.id ?: return@LaunchedEffect
        val key = lastReadAyah ?: return@LaunchedEffect
        val parts = key.split(":")
        val keySurahId = parts.getOrNull(0)?.toIntOrNull() ?: return@LaunchedEffect
        if (keySurahId != currentSurahId) return@LaunchedEffect

        val ayahNumber = parts.getOrNull(1)?.toIntOrNull() ?: return@LaunchedEffect
        val index = state.ayahs.indexOfFirst { it.number == ayahNumber }
        if (index >= 0) listState.scrollToItem(index)
    }

    Scaffold(
        topBar = {
            RamadanToolbar(
                title = "Quran",
                showBack = true,
                onBackClick = onBack
            )
        }
    ) { padding ->
        when {
            state.isLoading && state.ayahs.isEmpty() -> {
                QuranAyahsSkeleton(modifier = Modifier.padding(padding))
            }

            state.errorMessage != null && state.ayahs.isEmpty() -> {
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
                        .padding(16.dp)
                ) {
                    state.selectedSurah?.let { SurahHeaderCard(it) }

                    Spacer(modifier = Modifier.height(12.dp))

                    LazyColumn(state = listState) {
                        items(state.ayahs, key = { it.number }) { ayah ->
                            val currentSurahId = state.selectedSurah?.id ?: surahId
                            val key = "$currentSurahId:${ayah.number}"

                            AyahItem(
                                surahId = currentSurahId,
                                ayah = ayah,
                                isBookmarked = bookmarkedAyahs.contains(key),
                                onBookmarkClick = {
                                    viewModel.toggleBookmark(currentSurahId, ayah.number)
                                    viewModel.saveLastRead(currentSurahId, ayah.number)
                                },
                                onClick = {
                                    viewModel.saveLastRead(currentSurahId, ayah.number)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
