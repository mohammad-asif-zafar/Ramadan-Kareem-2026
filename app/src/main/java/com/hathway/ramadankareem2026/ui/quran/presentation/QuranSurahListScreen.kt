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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.allahnames.components.NamesDisplaySection
import com.hathway.ramadankareem2026.ui.commoncomponents.SearchFieldOutlinedText
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.components.ToolbarIcon
import com.hathway.ramadankareem2026.ui.icons.RamadanIcons
import com.hathway.ramadankareem2026.ui.navigation.Routes
import com.hathway.ramadankareem2026.ui.quran.domain.model.Surah
import com.hathway.ramadankareem2026.ui.quran.presentation.viewmodel.QuranBookmarkCountViewModel
import com.hathway.ramadankareem2026.ui.theme.Gold
import com.hathway.ramadankareem2026.ui.theme.Emerald

import androidx.compose.animation.Crossfade

import androidx.compose.foundation.layout.aspectRatio

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp


@Composable
fun QuranSurahListScreen(
    viewModel: QuranViewModel,
    quranBookmarkCountViewModel: QuranBookmarkCountViewModel,
    onBack: () -> Unit,
    onSurahClick: (Surah) -> Unit,
    navController: NavController,
) {
    val state by viewModel.state.collectAsState()
    var isGridView by remember { mutableStateOf(value = true) } // Layout mode state flag
    LaunchedEffect(Unit) {
        if (state.surahList.isEmpty() && !state.isLoading) {
            viewModel.loadSurahs()
        }
    }

    var searchQuery by remember { mutableStateOf("") }
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
                rightIcon1 = ToolbarIcon.Drawable(R.drawable.ic_saved),
                rightIcon1Badge = bookmarkCount,
                onRightIcon1Click = {
                    // Navigate to Quran bookmarks list
                    navController.navigate(Routes.QURAN_BOOKMARKS)
                },
                // 💡 Dynamically changes the icon shape to match the next available layout view
                rightIcon2 = if (isGridView) {
                    ToolbarIcon.Vector(RamadanIcons.GridViewIcon)
                } else {
                    ToolbarIcon.Vector(RamadanIcons.ListViewIcon)
                },
                onRightIcon2Click = {
                    isGridView = !isGridView
                }
            )
        }
    ) { padding ->
        when {
            state.isLoading && state.surahList.isEmpty() -> {
                QuranSurahListSkeleton(modifier = Modifier.padding(padding))
            }

            (state.errorMessage != null && state.surahList.isEmpty()) -> {
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
                    SearchFieldOutlinedText(
                        query = searchQuery,
                        onQueryChange = { searchQuery = it },
                        placeholderText = stringResource(R.string.search_surahs),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )

                    SurahsDisplaySection(
                        isGridView = isGridView,
                        filteredSurahs = filteredSurahs,
                        onSurahClick = onSurahClick,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

/*
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
                        color = Emerald.copy(alpha = 0.12f), shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = surah.id.toString(),
                    style = MaterialTheme.typography.labelMedium,
                    color = Emerald
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                // Arabic name (GOLD)
                Text(
                    text = surah.name,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Gold
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
*/




@Composable
fun SurahsDisplaySection(
    isGridView: Boolean,
    filteredSurahs: List<Surah>,
    onSurahClick: (Surah) -> Unit,
    modifier: Modifier = Modifier
) {
    Crossfade(
        targetState = isGridView,
        modifier = modifier,
        label = "SurahsLayoutSwitch"
    ) { gridActive ->
        if (gridActive) {
            // 🎴 OPTION 1: 2-by-2 Grid View Layout Pattern
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items = filteredSurahs, key = { it.id }) { surah ->
                    SurahGridCard(surah = surah) {
                        onSurahClick(surah)
                    }
                }
            }
        } else {
            // 📜 OPTION 2: Standard Full Width List View Layout Pattern
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items = filteredSurahs, key = { it.id }) { surah ->
                    SurahListRow(surah = surah) {
                        onSurahClick(surah)
                    }
                }
            }
        }
    }
}

/**
 * 🎴 Grid Item Component (2x2 Grid View layout)
 */
@Composable
private fun SurahGridCard(
    surah: Surah,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.85f),
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 2.dp,
        tonalElevation = 1.dp,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Centered ID Badge
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f), 
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = surah.id.toString(),
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Arabic Name
            Text(
                text = surah.name,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 24.sp, 
                    fontWeight = FontWeight.Bold
                ),
                color = Gold,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            // English Title Transliteration
            Text(
                text = surah.englishName,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 14.sp, 
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Translation Metadata Text Details
            Text(
                text = "${surah.numberOfAyahs} Ayahs • ${surah.revelationType}",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

/**
 * 📜 Row Item Component (Full Width List layout)
 */
@Composable
private fun SurahListRow(
    surah: Surah,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 2.dp,
        tonalElevation = 1.dp,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f), 
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = surah.id.toString(),
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = surah.englishName,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 16.sp, 
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = surah.name,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold, 
                            fontSize = 20.sp
                        ),
                        color = Gold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${surah.numberOfAyahs} ayahs • ${surah.revelationType}",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
