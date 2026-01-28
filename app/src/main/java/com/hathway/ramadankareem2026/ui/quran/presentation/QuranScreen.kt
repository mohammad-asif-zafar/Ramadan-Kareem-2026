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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.quran.components.AyahItem
import com.hathway.ramadankareem2026.ui.quran.components.SurahDropdown
import com.hathway.ramadankareem2026.ui.quran.components.SurahHeaderCard

@Composable
fun QuranScreen(
    viewModel: QuranViewModel,
    initialSurahId: Int,
    initialAyah: Int,
    onAyahClick: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val bookmarkedAyahs by viewModel.bookmarkedAyahs.collectAsState()
    val lastReadAyah by viewModel.lastReadAyah.collectAsState()

    val listState = rememberLazyListState()

    // Select surah from navigation
    LaunchedEffect(initialSurahId, state.surahList) {
        if (initialSurahId > 0) {
            state.surahList
                .firstOrNull { it.id == initialSurahId }
                ?.let(viewModel::onSurahSelected)
        }
    }

    // Scroll to ayah from navigation
    LaunchedEffect(initialAyah, state.ayahs) {
        if (initialAyah > 0) {
            val index = state.ayahs.indexOfFirst { it.number == initialAyah }
            if (index >= 0) listState.scrollToItem(index)
        }
    }

    // Resume last read
    LaunchedEffect(state.ayahs, lastReadAyah) {
        lastReadAyah?.let { key ->
            val ayahNumber = key.split(":").getOrNull(1)?.toIntOrNull()
            val index = state.ayahs.indexOfFirst { it.number == ayahNumber }
            if (index >= 0) listState.scrollToItem(index)
        }
    }

    Scaffold(
        topBar = {
            RamadanToolbar(
                title = stringResource(R.string.quran_reader),
                showBack = true,
                onBackClick = {},
                rightIcon1 = R.drawable.ic_saved,
                onRightIcon1Click = {},
                rightIcon2 = R.drawable.bell,
                onRightIcon2Click = {}
            )
        }
    ) { padding ->

        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.errorMessage != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.errorMessage ?: "Something went wrong",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            else -> {
                // ✅ NORMAL CONTENT
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp)
                ) {

                    SurahDropdown(
                        surahList = state.surahList,
                        selected = state.selectedSurah,
                        onSelect = viewModel::onSurahSelected
                    )

                    Spacer(Modifier.height(16.dp))

                    state.selectedSurah?.let {
                        SurahHeaderCard(it)
                    }

                    Spacer(Modifier.height(12.dp))

                    LazyColumn(state = listState) {
                        items(state.ayahs) { ayah ->
                            val surahId = state.selectedSurah?.id ?: return@items
                            val key = "$surahId:${ayah.number}"

                            AyahItem(
                                surahId = surahId,
                                ayah = ayah,
                                isBookmarked = bookmarkedAyahs.contains(key),
                                onBookmarkClick = {
                                    viewModel.toggleBookmark(surahId, ayah.number)
                                    viewModel.saveLastRead(surahId, ayah.number)
                                },
                                onClick = {
                                    onAyahClick(ayah.number)
                                    viewModel.saveLastRead(surahId, ayah.number)
                                }
                            )
                        }
                    }
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun QuranScreenPreview() {/* QuranScreen(
         viewModel = FakeQuranViewModel()
     )*/
}
