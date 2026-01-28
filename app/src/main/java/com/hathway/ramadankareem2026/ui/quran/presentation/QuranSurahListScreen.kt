package com.hathway.ramadankareem2026.ui.quran.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.hathway.ramadankareem2026.ui.quran.domain.model.Surah

@Composable
fun QuranSurahListScreen(
    viewModel: QuranViewModel,
    onBack: () -> Unit,
    onSurahClick: (Surah) -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        if (state.surahList.isEmpty() && !state.isLoading) {
            viewModel.loadSurahs()
        }
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
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.surahList, key = { it.id }) { surah ->
                        SurahRow(
                            surah = surah,
                            onClick = { onSurahClick(surah) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SurahRow(
    surah: Surah,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text(
                text = "${surah.id}. ${surah.name}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "${surah.englishName} • ${surah.numberOfAyahs} ayahs • ${surah.revelationType}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
