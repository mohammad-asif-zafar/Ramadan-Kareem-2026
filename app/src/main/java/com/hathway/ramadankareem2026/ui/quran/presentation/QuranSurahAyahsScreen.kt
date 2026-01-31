package com.hathway.ramadankareem2026.ui.quran.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.quran.components.AyahAudioPlayer
import com.hathway.ramadankareem2026.ui.quran.domain.model.Ayah
import com.hathway.ramadankareem2026.ui.quran.domain.model.Surah

@Composable
fun QuranSurahAyahsScreen(
    viewModel: QuranViewModel, surahId: Int, onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val bookmarkedAyahs by viewModel.bookmarkedAyahs.collectAsState()
    val lastReadAyah by viewModel.lastReadAyah.collectAsState()
    val currentlyPlayingAyah by viewModel.currentlyPlayingAyah.collectAsState()
    val isAudioPlaying by viewModel.isAudioPlaying.collectAsState()

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
                title = state.selectedSurah?.englishName ?: "Quran",
                showBack = true,
                onBackClick = onBack,
                        // Bookmarks
                        rightIcon1 = R.drawable.ic_saved,
            )
        }) { padding ->
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
                        .padding(horizontal = 24.dp)
                ) {
                    // 🕌 TOP SURAH HEADER CARD
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        shape = RoundedCornerShape(20.dp),
                        tonalElevation = 2.dp,
                        color = MaterialTheme.colorScheme.surface
                    ) {
                        state.selectedSurah?.let { surah ->
                            SurahHeaderCardNew(surah)
                        }
                    }

                    // 📖 AYAH LIST
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(state.ayahs, key = { it.number }) { ayah ->
                            val currentSurahId = state.selectedSurah?.id ?: surahId
                            val key = "$currentSurahId:${ayah.number}"

                            AyahCard(
                                ayah = ayah,
                                isBookmarked = bookmarkedAyahs.contains(key),
                                isPlaying = currentlyPlayingAyah == ayah.audio && isAudioPlaying,
                                onBookmarkClick = {
                                    viewModel.toggleBookmark(currentSurahId, ayah.number)
                                    viewModel.saveLastRead(currentSurahId, ayah.number)
                                },
                                onAudioClick = {
                                    viewModel.toggleAudioPlayback(ayah.audio)
                                },
                                onClick = {
                                    viewModel.saveLastRead(currentSurahId, ayah.number)
                                })
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SurahHeaderCardNew(surah: Surah) {
    Column(
        modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = surah.name,
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = surah.englishName,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "${surah.numberOfAyahs} verses • ${surah.revelationType}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun AyahCard(
    ayah: Ayah,
    isBookmarked: Boolean,
    isPlaying: Boolean,
    onBookmarkClick: () -> Unit,
    onAudioClick: () -> Unit,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(20.dp),
        tonalElevation = 1.dp,
        shadowElevation = 2.dp,
        color = MaterialTheme.colorScheme.surface,
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Top row with ayah number and bookmark
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Ayah number badge
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = ayah.number.toString(),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Ayah ${ayah.number}",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f)
                )

                // Bookmark button
              /*  IconButton(onClick = onBookmarkClick) {
                    Icon(
                        imageVector = if (isBookmarked)
                            Icons.Default.Bookmark
                        else Icons.Default.BookmarkBorder,
                        contentDescription = "Bookmark ayah",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }*/
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Arabic text
            Text(
                text = ayah.arabicText,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Translation
            Text(
                text = ayah.translation,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
            )

            // Audio player (if available)
            if (ayah.audio != null) {
                Spacer(modifier = Modifier.height(12.dp))
                AyahAudioPlayer(
                    audioUrl = ayah.audio,
                    isPlaying = isPlaying,
                    onPlayPause = onAudioClick
                )
            }
        }
    }
}
