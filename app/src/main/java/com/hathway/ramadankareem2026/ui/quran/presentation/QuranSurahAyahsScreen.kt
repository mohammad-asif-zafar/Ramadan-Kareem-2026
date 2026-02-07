package com.hathway.ramadankareem2026.ui.quran.presentation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.navigation.Routes
import com.hathway.ramadankareem2026.ui.quran.domain.model.Ayah
import com.hathway.ramadankareem2026.ui.quran.presentation.viewmodel.QuranBookmarkCountViewModel
import com.hathway.ramadankareem2026.ui.quran.presentation.viewmodel.QuranBookmarkViewModel

@Composable
fun QuranSurahAyahsScreen(
    viewModel: QuranViewModel,
    surahId: Int,
    onBack: () -> Unit,
    quranBookmarkViewModel: QuranBookmarkViewModel,
    quranBookmarkCountViewModel: QuranBookmarkCountViewModel,
    navController: NavController
) {
    val state by viewModel.state.collectAsState()
    val bookmarkedAyahs by viewModel.bookmarkedAyahs.collectAsState()
    val lastReadAyah by viewModel.lastReadAyah.collectAsState()
   // val currentlyPlayingAyah by viewModel.currentlyPlayingAyah.collectAsState()
    val isAudioPlaying by viewModel.isAudioPlaying.collectAsState()

    // Surah-level bookmark state
    val isBookmarked by quranBookmarkViewModel.isBookmarked(surahId.toString())
        .collectAsStateWithLifecycle(initialValue = false)

    val bookmarkCount by quranBookmarkCountViewModel.quranBookmarkCount.collectAsStateWithLifecycle(
        initialValue = 0
    )

    val listState = rememberLazyListState()

    val collapseRangePx = with(LocalDensity.current) { 120.dp.toPx() }

    val collapseProgress by remember {
        derivedStateOf {
            val offset = listState.firstVisibleItemScrollOffset.toFloat()
            (offset / collapseRangePx).coerceIn(0f, 1f)
        }
    }
    val toolbarHeight by animateDpAsState(
        targetValue = lerp(96.dp, 56.dp, collapseProgress),
        animationSpec = tween(200),
        label = "toolbarHeight"
    )

    val subtitleAlpha by animateFloatAsState(
        targetValue = 1f - collapseProgress, animationSpec = tween(150), label = "subtitleAlpha"
    )


    LaunchedEffect(surahId) {
        quranBookmarkViewModel.checkBookmarkStatus(surahId.toString())
        // Set up callback for immediate Quran badge updates with delta
        quranBookmarkViewModel.setBookmarkCountChangedCallback { delta ->
            quranBookmarkCountViewModel.updateQuranBookmarkCountImmediate(delta)
        }
    }

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


    Scaffold(topBar = {
        val surah = state.selectedSurah

        RamadanToolbar(
            title = surah?.name ?: stringResource(R.string.feature_quran),
            subtitle = surah?.englishName,/*meta = surah?.let {
                "${it.numberOfAyahs} verses • ${it.revelationType}"
            },*/
            toolbarHeight = toolbarHeight,
            subtitleAlpha = subtitleAlpha,
            metaAlpha = 1f - collapseProgress,
            metaOffsetY = lerp(0.dp, (-16).dp, collapseProgress),
            showBack = true,
            onBackClick = onBack,
            rightIcon1 = R.drawable.ic_saved,
            rightIcon1Badge = bookmarkCount,
            onRightIcon1Click = {
                navController.navigate(Routes.QURAN_BOOKMARKS)
            },
            onRightIcon2Click = {
                surah?.let {
                    quranBookmarkViewModel.toggleBookmark(
                        surahId = it.id.toString(), title = it.englishName, content = it.name
                    )
                }
            })


    }, bottomBar = {
        val isPlaying by viewModel.isAudioPlaying.collectAsState()
        val hasStarted by viewModel.hasStartedPlayback.collectAsState()

        SurahAudioPlayerBar(
            isPlaying = isPlaying,
            onPlay = {
                if (hasStarted) {
                    viewModel.resumeAudio()   // ✅ resume
                } else {
                    viewModel.playSurah(state.ayahs) // ✅ first play
                }
            },
            onPause = {
                viewModel.pauseAudio()
            },
            onStop = {
                viewModel.stopAudio()
            }
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
                        .padding(top = 12.dp, start = 24.dp, end = 24.dp)
                ) {


                    // SURAH BOOKMARK BUTTON - Enhanced with animations
                    val bookmarkScale by animateFloatAsState(
                        targetValue = if (isBookmarked) 1.2f else 1.0f,
                        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                        label = "bookmark_scale"
                    )

                    val bookmarkBackgroundColor by animateColorAsState(
                        targetValue = if (isBookmarked) MaterialTheme.colorScheme.primaryContainer
                        else MaterialTheme.colorScheme.surface,
                        animationSpec = tween(durationMillis = 300),
                        label = "bookmark_background_color"
                    )

                    val bookmarkIconColor by animateColorAsState(
                        targetValue = if (isBookmarked) MaterialTheme.colorScheme.onPrimaryContainer
                        else MaterialTheme.colorScheme.primary,
                        animationSpec = tween(durationMillis = 300),
                        label = "bookmark_icon_color"
                    )

                    val bookmarkBorderColor by animateColorAsState(
                        targetValue = if (isBookmarked) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.outline,
                        animationSpec = tween(durationMillis = 300),
                        label = "bookmark_border_color"
                    )

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {
                                val surah = state.selectedSurah
                                if (surah != null) {
                                    quranBookmarkViewModel.toggleBookmark(
                                        surahId = surah.id.toString(),
                                        title = surah.englishName,
                                        content = surah.name
                                    )
                                }
                            }
                            .shadow(
                                elevation = if (isBookmarked) 8.dp else 2.dp,
                                shape = RoundedCornerShape(12.dp)
                            ),
                        shape = RoundedCornerShape(12.dp),
                        color = bookmarkBackgroundColor,
                        border = androidx.compose.foundation.BorderStroke(
                            width = if (isBookmarked) 2.dp else 1.dp, color = bookmarkBorderColor
                        )) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "Bookmark this Surah",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = if (isBookmarked) MaterialTheme.colorScheme.onPrimaryContainer
                                    else MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = if (isBookmarked) {
                                        "✅ Bookmarked! Tap to remove"
                                    } else {
                                        "Tap to add to your bookmarks"
                                    },
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (isBookmarked) MaterialTheme.colorScheme.onPrimaryContainer.copy(
                                        alpha = 0.8f
                                    )
                                    else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .scale(bookmarkScale)
                                    .background(
                                        color = if (isBookmarked) MaterialTheme.colorScheme.primary.copy(
                                            alpha = 0.1f
                                        )
                                        else Color.Transparent, shape = CircleShape
                                    )
                                    .padding(8.dp)
                            ) {
                                Icon(
                                    imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                    contentDescription = if (isBookmarked) "Remove bookmark" else "Add bookmark",
                                    tint = bookmarkIconColor,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }
                    }

                    //  AYAH LIST
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(0.dp),
                        contentPadding = PaddingValues(bottom = 24.dp)
                    ) {
                        items(state.ayahs, key = { it.number }) { ayah ->
                            val currentSurahId = state.selectedSurah?.id ?: surahId
                            val key = "$currentSurahId:${ayah.number}"

                            AyahCard(
                                ayah = ayah,
                                isPlaying = true,
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
private fun AyahCard(
    ayah: Ayah, isPlaying: Boolean, onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        color = if (isPlaying) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
        else MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp, horizontal = 12.dp)
        ) {

            // 🔹 AYAH NUMBER (SUBTLE, QURAN STYLE)
            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 3.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.08f)
                ) {
                    Text(
                        text = ayah.number.toString(),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 🔹 ARABIC TEXT (PRIMARY FOCUS)
            Text(
                text = ayah.arabicText,
                style = MaterialTheme.typography.headlineSmall.copy(
                    lineHeight = 40.sp
                ),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )

            // 🔹 OPTIONAL TRANSLATION (SOFTER)
            if (ayah.translation.isNotBlank()) {
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = ayah.translation,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.65f),
                    lineHeight = 20.sp
                )
            }

            // 🔹 SOFT DIVIDER
            Spacer(modifier = Modifier.height(18.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.04f))

            )
        }
    }
}
