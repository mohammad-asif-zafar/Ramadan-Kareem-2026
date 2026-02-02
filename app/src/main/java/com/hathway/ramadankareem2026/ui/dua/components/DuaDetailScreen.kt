package com.hathway.ramadankareem2026.ui.dua.components

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.core.service.DuaTtsNotification
import com.hathway.ramadankareem2026.core.service.DuaTtsService
import com.hathway.ramadankareem2026.core.tts.TtsActions
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.dua.model.DuaItem
import com.hathway.ramadankareem2026.ui.dua.presentation.viewmodel.DuaBookmarkCountViewModel
import com.hathway.ramadankareem2026.ui.dua.viewmodel.DuaBookmarkViewModel
import com.hathway.ramadankareem2026.ui.navigation.Routes
import com.hathway.ramadankareem2026.ui.theme.RamadanKareemTheme

@SuppressLint("MissingPermission")
@Composable
fun DuaDetailScreen(
    dua: DuaItem,
    onBack: () -> Unit,
    navController: NavController,
    bookmarkViewModel: DuaBookmarkViewModel,
    duaBookmarkCountViewModel: DuaBookmarkCountViewModel
) {
    val isBookmarked by bookmarkViewModel.isBookmarked(dua.id)
        .collectAsStateWithLifecycle(initialValue = false)
    val bookmarkCount by duaBookmarkCountViewModel.duaBookmarkCount.collectAsStateWithLifecycle(initialValue = 0)

    LaunchedEffect(dua.id) {
        bookmarkViewModel.checkBookmarkStatus(dua.id)
        // Set up callback for immediate dua badge updates with delta
        bookmarkViewModel.setBookmarkCountChangedCallback { delta ->
            duaBookmarkCountViewModel.updateDuaBookmarkCountImmediate(delta)
        }
    }
    Scaffold(

        /*  Top App Bar */
        topBar = {
            RamadanToolbar(
                title = dua.categoryId,
                showBack = true,
                onBackClick = onBack,
                rightIcon1 = R.drawable.ic_saved,
                rightIcon1Badge = bookmarkCount,
                onRightIcon1Click = {
                    // Navigate to dua bookmarks list
                    navController.navigate(Routes.DUA_BOOKMARKS)
                },
                onRightIcon2Click = { })
        },

        ) { padding ->

        val context = LocalContext.current
        var isPlaying by remember { mutableStateOf(false) }

        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                item {
                    Spacer(modifier = Modifier.height(4.dp))
                }
                item {
                    Text(
                        text = dua.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = MaterialTheme.shapes.small,
                            color = if (isBookmarked) MaterialTheme.colorScheme.primaryContainer
                            else MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                    tint = if (isBookmarked) MaterialTheme.colorScheme.onPrimaryContainer
                                    else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isBookmarked) stringResource(R.string.bookmarked) else stringResource(
                                        R.string.not_bookmarked
                                    ),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (isBookmarked) MaterialTheme.colorScheme.onPrimaryContainer
                                    else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        TextButton(
                            onClick = {
                                bookmarkViewModel.toggleBookmark(
                                    itemId = dua.id, title = dua.title, content = dua.arabic
                                )
                            }) {
                            Text(
                                text = if (isBookmarked) stringResource(R.string.remove_bookmark) else stringResource(
                                    R.string.add_bookmark
                                ), style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                }

                item {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = dua.arabic,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            lineHeight = 40.sp
                        )
                    }
                }

                item {
                    Text(
                        text = dua.transliteration,
                        style = MaterialTheme.typography.bodyLarge,
                        fontStyle = FontStyle.Italic,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                item {
                    Text(
                        text = dua.translation,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        lineHeight = 24.sp
                    )
                }

                item {
                    Text(
                        text = "— ${dua.source}",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
            // Floating Play Button
            FloatingActionButton(
                onClick = {
                    if (isPlaying) {
                        // Pause playback
                        val intent = Intent(context, DuaTtsService::class.java).apply {
                            action = TtsActions.STOP
                        }
                        context.startService(intent)
                        isPlaying = false

                        // Cancel notification
                        NotificationManagerCompat.from(context)
                            .cancel(DuaTtsNotification.NOTIFICATION_ID)
                    } else {
                        // Start playback
                        val intent = Intent(context, DuaTtsService::class.java).apply {
                            action = TtsActions.READ
                            putExtra(TtsActions.EXTRA_TEXT, dua.arabic)
                        }
                        context.startService(intent)
                        isPlaying = true

                        // Show notification
                        val notification = DuaTtsNotification.build(
                            context = context, isPlaying = true
                        )
                        NotificationManagerCompat.from(context)
                            .notify(DuaTtsNotification.NOTIFICATION_ID, notification)
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 16.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                    contentDescription = if (isPlaying) stringResource(R.string.pause) else stringResource(
                        R.string.play
                    ),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}


@Preview(
    name = "Dua Detail Screen – Main", device = Devices.PIXEL_6, showBackground = true
)
@Composable
fun DuaDetailScreenPreview() {
    RamadanKareemTheme {
        // Create mock ViewModels for preview
        val mockDuaBookmarkCountViewModel = DuaBookmarkCountViewModel(Application())
        val mockBookmarkViewModel = DuaBookmarkViewModel(Application())
        
        DuaDetailScreen(
            dua = DuaItem(
                id = "1",
                title = "Ramadan Moon Sighting Duʿāʾ",
                arabic = "اللَّهُمَّ أَهْلِلْهُ عَلَيْنَا بِالْيُمْنِ وَالإِيمَانِ",
                transliteration = "Allahumma ahlilhu 'alaynā bil-yumni wal-īmān",
                translation = "O Allah, bring it upon us with blessings and faith.",
                source = "Tirmidhi",
                categoryId = "ramadan"
            ), 
            onBack = {}, 
            navController = rememberNavController(),
            bookmarkViewModel = mockBookmarkViewModel,
            duaBookmarkCountViewModel = mockDuaBookmarkCountViewModel
        )
    }
}
