package com.hathway.ramadankareem2026.ui.allahnames

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import android.app.Application
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.allahnames.domain.model.AllahName
import com.hathway.ramadankareem2026.ui.allahnames.viewmodel.AllahNamesBookmarkViewModel
import com.hathway.ramadankareem2026.ui.bookmarks.viewmodel.BookmarkCountViewModel
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.navigation.Routes
import com.hathway.ramadankareem2026.ui.theme.LightTextPrimary
import com.hathway.ramadankareem2026.ui.theme.RamadanGold
import com.hathway.ramadankareem2026.ui.theme.RamadanGreen

@Composable
fun AllahNameDetailScreen(
    name: AllahName, 
    onBack: () -> Unit,
    navController: NavController,
    bookmarkViewModel: AllahNamesBookmarkViewModel,
    sharedBookmarkCountViewModel: BookmarkCountViewModel
) {
    val isBookmarked by bookmarkViewModel.isBookmarked(name.id.toString())
        .collectAsStateWithLifecycle(initialValue = false)
    val bookmarkCount by sharedBookmarkCountViewModel.bookmarkCount.collectAsStateWithLifecycle(initialValue = 0)

    LaunchedEffect(name.id) {
        bookmarkViewModel.checkBookmarkStatus(name.id.toString())
        // Set up callback for immediate badge updates with delta
        bookmarkViewModel.setBookmarkCountChangedCallback { delta ->
            sharedBookmarkCountViewModel.updateBookmarkCountImmediate(delta)
        }
    }

    Scaffold(
        topBar = {
            RamadanToolbar(
                title = name.transliteration, 
                showBack = true, 
                onBackClick = onBack,
                rightIcon1 = R.drawable.ic_saved,
                rightIcon1Badge = bookmarkCount,
                onRightIcon1Click = {
                    // Navigate to bookmarks list
                    navController.navigate(Routes.BOOKMARKS)
                }
            )
        }, 
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }

                item {
                    Text(
                        text = name.arabic,
                        style = MaterialTheme.typography.displayLarge,
                        color = LightTextPrimary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    Text(
                        text = name.transliteration,
                        style = MaterialTheme.typography.titleMedium,
                        color = RamadanGreen,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    Text(
                        text = name.meaning,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
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
                                androidx.compose.material3.Icon(
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
                                    itemId = name.id.toString(), 
                                    title = "${name.transliteration} - ${name.english}", 
                                    content = name.arabic
                                )
                                // Badge update is now handled by callback
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
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = name.english,
                            modifier = Modifier.padding(24.dp),
                            style = MaterialTheme.typography.bodyLarge,
                            color = RamadanGold,
                            textAlign = TextAlign.Center,
                            lineHeight = 26.sp
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}


@Preview(
    name = "Allah Name Detail – Light", showBackground = true
)
@Composable
fun AllahNameDetailPreviewLight() {
    MaterialTheme {
        // Create mock ViewModels for preview
        val mockBookmarkCountViewModel = BookmarkCountViewModel(android.app.Application())
        val mockBookmarkViewModel = AllahNamesBookmarkViewModel(android.app.Application())
        
        AllahNameDetailScreen(
            name = AllahName(
                id = 4,
                arabic = "الْقُدُّوسُ",
                transliteration = "Al-Quddoos",
                english = "The Most Holy",
                meaning = "The One who is pure from any imperfection and clear from children and adversaries."
            ), 
            onBack = {},
            navController = rememberNavController(),
            bookmarkViewModel = mockBookmarkViewModel,
            sharedBookmarkCountViewModel = mockBookmarkCountViewModel)
    }
}
