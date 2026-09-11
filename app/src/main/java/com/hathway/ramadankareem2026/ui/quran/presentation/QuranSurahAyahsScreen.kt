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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.core.localization.LocalizationManager
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.components.ToolbarIcon
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
    val context = LocalContext.current
    val localizationManager = LocalizationManager(context)
    val currentLanguage = localizationManager.getCurrentLanguage()
    
    val state by viewModel.state.collectAsState()
    val lastReadAyah by viewModel.lastReadAyah.collectAsState()
    val currentPlayingIndex by viewModel.currentPlayingIndex.collectAsState()
    val isAudioPlaying by viewModel.isAudioPlaying.collectAsState()

    // Surah-level bookmark state
    val isBookmarked by quranBookmarkViewModel.isBookmarked(surahId.toString())
        .collectAsStateWithLifecycle(initialValue = false)

    val bookmarkCount by quranBookmarkCountViewModel.quranBookmarkCount.collectAsStateWithLifecycle(
        initialValue = 0
    )
    
    // Helper function to get localized bookmark text
    fun getBookmarkText(isBookmarked: Boolean): String {
        return if (isBookmarked) {
            when (currentLanguage) {
                "hi" -> "✅ बुकमार्क किया गया! टैप करके हटाएं"
                "ur" -> "✅ بک مارک کردہ! ہٹانے کے لیے ٹیپ کریں"
                "ms" -> "✅ Ditanda! Ketik untuk keluarkan"
                else -> "✅ Bookmarked! Tap to remove"
            }
        } else {
            when (currentLanguage) {
                "hi" -> "अपने बुकमार्क में जोड़ने के लिए टैप करें"
                "ur" -> "اپنے بک مارکس میں شامل کرنے کے لیے ٹیپ کریں"
                "ms" -> "Ketik untuk tambah ke penanda buku"
                else -> "Tap to add to your bookmarks"
            }
        }
    }

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

    // Resume last read within current surah OR scroll to playing ayah
    LaunchedEffect(state.ayahs, lastReadAyah, currentPlayingIndex, state.selectedSurah?.id) {
        val currentSurahId = state.selectedSurah?.id ?: return@LaunchedEffect
        
        // Priority 1: Scroll to currently playing
        if (isAudioPlaying && currentPlayingIndex >= 0 && currentPlayingIndex < state.ayahs.size) {
            listState.animateScrollToItem(currentPlayingIndex)
            return@LaunchedEffect
        }

        // Priority 2: Scroll to last read
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
            val surah = state.selectedSurah

            RamadanToolbar(
                title = surah?.name ?: stringResource(R.string.feature_quran),
                subtitle = surah?.englishName,
                toolbarHeight = toolbarHeight,
                subtitleAlpha = subtitleAlpha,
                metaAlpha = 1f - collapseProgress,
                metaOffsetY = lerp(0.dp, (-16).dp, collapseProgress),
                showBack = true,
                onBackClick = onBack,
                rightIcon1 = ToolbarIcon.Drawable(R.drawable.ic_saved),
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
        },
        bottomBar = {
            val isPlaying by viewModel.isAudioPlaying.collectAsState()
            val hasStarted by viewModel.hasStartedPlayback.collectAsState()

            SurahAudioPlayerBar(
                isPlaying = isPlaying,
                onPlay = {
                    if (hasStarted) {
                        viewModel.resumeAudio()
                    } else {
                        viewModel.playSurah(state.ayahs)
                    }
                },
                onPause = {
                    viewModel.pauseAudio()
                },
                onStop = {
                    viewModel.stopAudio()
                }
            )
        },
        containerColor = if (androidx.compose.foundation.isSystemInDarkTheme()) Color(0xFF0D1B16) else MaterialTheme.colorScheme.background
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
                ) {
                    //  AYAH LIST
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(0.dp),
                        contentPadding = PaddingValues(bottom = 100.dp)
                    ) {
                        item {
                            // SURAH BOOKMARK BUTTON - Refined and moved inside LazyColumn
                            val isDark = androidx.compose.foundation.isSystemInDarkTheme()
                            
                            val bookmarkScale by animateFloatAsState(
                                targetValue = if (isBookmarked) 1.1f else 1.0f,
                                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                                label = "bookmark_scale"
                            )

                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 16.dp)
                                    .clickable {
                                        val surah = state.selectedSurah
                                        if (surah != null) {
                                            quranBookmarkViewModel.toggleBookmark(
                                                surahId = surah.id.toString(),
                                                title = surah.englishName,
                                                content = surah.name
                                            )
                                        }
                                    },
                                shape = RoundedCornerShape(20.dp),
                                color = if (isBookmarked) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                                border = if (isBookmarked) androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)) else null
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = if (isBookmarked) "Surah Bookmarked" else "Bookmark this Surah",
                                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                            color = if (isBookmarked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = getBookmarkText(isBookmarked),
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }

                                    Icon(
                                        imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                        contentDescription = null,
                                        tint = if (isBookmarked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(24.dp).scale(bookmarkScale)
                                    )
                                }
                            }
                        }

                        items(state.ayahs.size) { index ->
                            val ayah = state.ayahs[index]
                            val currentSurahId = state.selectedSurah?.id ?: surahId

                            AyahCard(
                                ayah = ayah,
                                isPlaying = isAudioPlaying && currentPlayingIndex == index,
                                onClick = {
                                    viewModel.saveLastRead(currentSurahId, ayah.number)
                                    viewModel.playSurah(state.ayahs, index)
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
    ayah: Ayah, 
    isPlaying: Boolean, 
    onClick: () -> Unit
) {
    val isDark = androidx.compose.foundation.isSystemInDarkTheme()
    val backgroundColor = if (isPlaying) {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
    } else {
        Color.Transparent
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp, horizontal = 16.dp)
        ) {
            // AYAH NUMBER
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = ayah.number.toString(),
                    style = MaterialTheme.typography.labelSmall,
                    color = if (isPlaying) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier
                        .background(
                            color = (if (isPlaying) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline).copy(alpha = 0.1f),
                            shape = CircleShape
                        )
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ARABIC TEXT
            Text(
                text = ayah.arabicText,
                style = MaterialTheme.typography.headlineMedium.copy(
                    lineHeight = 52.sp,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = if (isPlaying) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )

            // TRANSLATION
            if (ayah.translation.isNotBlank()) {
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = ayah.translation,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        lineHeight = 26.sp,
                        fontSize = 16.sp
                    ),
                    color = if (isPlaying) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // DIVIDER
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
            )
        }
    }
}
