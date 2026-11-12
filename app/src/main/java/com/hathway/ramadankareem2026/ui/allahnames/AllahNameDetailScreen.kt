package com.hathway.ramadankareem2026.ui.allahnames

import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.ui.theme.Emerald
import android.app.Application
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.allahnames.domain.model.AllahName
import com.hathway.ramadankareem2026.ui.allahnames.presentation.viewmodel.AllahNameBookmarkCountViewModel
import com.hathway.ramadankareem2026.ui.allahnames.viewmodel.AllahNamesBookmarkViewModel
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.components.ToolbarIcon
import com.hathway.ramadankareem2026.ui.navigation.Routes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.ui.layout.ContentScale
import com.hathway.ramadankareem2026.ui.theme.NoorMuslimCompanionTheme

@Composable
fun AllahNameDetailScreen(
    name: AllahName,
    onBack: () -> Unit,
    navController: NavController,
    bookmarkViewModel: AllahNamesBookmarkViewModel,
    allahNameBookmarkCountViewModel: AllahNameBookmarkCountViewModel
) {
    val isBookmarked by bookmarkViewModel.isBookmarked(name.id.toString())
        .collectAsStateWithLifecycle(initialValue = false)
    val bookmarkCount by allahNameBookmarkCountViewModel.allahNameBookmarkCount.collectAsStateWithLifecycle(
        initialValue = 0
    )

    LaunchedEffect(name.id) {
        bookmarkViewModel.checkBookmarkStatus(name.id.toString())
        bookmarkViewModel.setBookmarkCountChangedCallback { delta ->
            allahNameBookmarkCountViewModel.updateAllahNameBookmarkCountImmediate(delta)
        }
    }

    AllahNameDetailContent(
        name = name,
        isBookmarked = isBookmarked,
        bookmarkCount = bookmarkCount,
        onBack = onBack,
        onBookmarkToggle = {
            bookmarkViewModel.toggleBookmark(
                itemId = name.id.toString(),
                title = "${name.transliteration} - ${name.english}",
                content = name.arabic
            )
        },
        onNavigateToBookmarks = {
            navController.navigate(Routes.ALLAH_NAME_BOOKMARKS)
        })
}

/**
 * Pure display composable built entirely without ViewModel initialization hooks,
 * allowing Android Studio previews to map layouts across themes safely.
 */
@Composable
private fun AllahNameDetailContent(
    name: AllahName,
    isBookmarked: Boolean,
    bookmarkCount: Int,
    onBack: () -> Unit,
    onBookmarkToggle: () -> Unit,
    onNavigateToBookmarks: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isDark = MaterialTheme.colorScheme.background.luminance() < 0.5f

    // Since background asset bg_allah_detail_screen is light cream, accent text must always be dark green for high legibility
    val brandAccentColor = Color(0xFF0F3D30)
    val cardContainerColor = if (isDark) Color(0xFF1B252E) else Color.White.copy(alpha = 0.95f)
    val textColor = if (isDark) Color.White else Color(0xFF101820)

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // 1. Background Image Layer
        Image(
            painter = painterResource(id = R.drawable.bg_allah_detail_screen),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // 2. Foreground User Interface
        Scaffold(
            topBar = {
                RamadanToolbar(
                    title = name.transliteration,
                    showBack = true,
                    onBackClick = onBack,
                    rightIcon1 = ToolbarIcon.Drawable(R.drawable.ic_saved),
                    rightIcon1Badge = bookmarkCount,
                    onRightIcon1Click = onNavigateToBookmarks,
                    backgroundColor = if (isDark) Color(0xFF0F1720) else Color.White,
                    contentColor = if (isDark) Color.White else Color.Black
                )
            }, containerColor = Color.Transparent
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item { Spacer(modifier = Modifier.height(8.dp)) }

                    item {
                        OrnamentalNameBox(
                            arabicText = name.arabic, tintColor = brandAccentColor
                        )
                    }

                    item {
                        Text(
                            text = name.transliteration,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold, fontSize = 28.sp
                            ),
                            color = brandAccentColor,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = if (isBookmarked) Emerald else cardContainerColor,
                                shadowElevation = 2.dp
                            ) {
                                Row(
                                    modifier = Modifier.padding(
                                        horizontal = 16.dp, vertical = 10.dp
                                    ), verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp),
                                        tint = if (isBookmarked) Color.White else textColor
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = if (isBookmarked) stringResource(R.string.bookmarked) else stringResource(
                                            R.string.not_bookmarked
                                        ),
                                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                                        color = if (isBookmarked) Color.White else textColor
                                    )
                                }
                            }

                            TextButton(onClick = onBookmarkToggle) {
                                Text(
                                    text = if (isBookmarked) stringResource(R.string.remove_bookmark) else stringResource(
                                        R.string.add_bookmark
                                    ),
                                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                                    color = Color(0xFF0F3D30)
                                )
                            }
                        }
                    }

                    item {
                        // Premium responsive dynamic design surface container for the meaning
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(24.dp),
                            color = cardContainerColor,
                            shadowElevation = 4.dp
                        ) {
                            Text(
                                text = name.meaning,
                                modifier = Modifier.padding(horizontal = 24.dp, vertical = 28.dp),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = 16.sp,
                                    lineHeight = 26.sp,
                                    fontWeight = FontWeight.Medium,
                                    letterSpacing = 0.25.sp
                                ),
                                color = textColor,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    item { Spacer(modifier = Modifier.height(24.dp)) }

                }
            }
        }
    }
}

@Composable
fun OrnamentalNameBox(
    arabicText: String, tintColor: Color, modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1.1f), // Dynamically calculates frame aspect footprints over hardcoded sizes
        contentAlignment = Alignment.Center
    ) {
// 1. Background Ring Image Asset Layer
        Image(
            painter = painterResource(R.drawable.bg_ornamental_ring),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(0.9f),
            contentScale = ContentScale.Fit
        )
// 2. Foreground Dynamic Text Content Layer (Perfecting Centering Big Script)
        Text(
            text = arabicText,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            color = tintColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(40.dp)
        )
    }
}

/* ----------------------------------------------------
• STRUCTURAL DEV PREVIEWS (DARK THEME COMPRESSED FIRST)
• ---------------------------------------------------*/
@Preview(name = "Dark Theme Preview", showBackground = true, widthDp = 360, heightDp = 760)
@Composable
fun AllahNameDetailPreviewDark() {
    NoorMuslimCompanionTheme(darkTheme = true) {
        AllahNameDetailContent(
            name = AllahName(
                id = 4,
                arabic = "الْقُدُّوسُ",
                transliteration = "Al-Quddoos",
                english = "The Most Holy",
                meaning = "The One who is pure from any imperfection and clear from children and adversaries."
            ),
            isBookmarked = true,
            bookmarkCount = 3,
            onBack = {},
            onBookmarkToggle = {},
            onNavigateToBookmarks = {})
    }
}


@Preview(
    name = "Allah Name Detail – Light", showBackground = true
)
@Composable
fun AllahNameDetailPreviewLight() {
    MaterialTheme {
        // Create mock ViewModels for preview
        val mockAllahNameBookmarkCountViewModel = AllahNameBookmarkCountViewModel(Application())
        val mockBookmarkViewModel = AllahNamesBookmarkViewModel(Application())

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
            allahNameBookmarkCountViewModel = mockAllahNameBookmarkCountViewModel
        )
    }
}
