package com.hathway.ramadankareem2026.ui.allahnames

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import android.app.Application
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.allahnames.components.AllahNameCard
import com.hathway.ramadankareem2026.ui.allahnames.domain.model.AllahName
import com.hathway.ramadankareem2026.ui.allahnames.viewmodel.AllahNamesBookmarkViewModel
import com.hathway.ramadankareem2026.ui.bookmarks.viewmodel.BookmarkCountViewModel
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.navigation.Routes

@Composable
fun AllahNamesScreen(
    names: List<AllahName>, 
    onBack: () -> Unit, 
    onNameClick: (AllahName) -> Unit,
    navController: NavController,
    sharedBookmarkCountViewModel: BookmarkCountViewModel
) {
    var searchQuery by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val bookmarkCount by sharedBookmarkCountViewModel.bookmarkCount.collectAsStateWithLifecycle(initialValue = 0)
    
    // Create bookmark ViewModel for immediate updates
    val bookmarkViewModel: AllahNamesBookmarkViewModel = viewModel()
    
    // Set up callback for immediate badge updates
    LaunchedEffect(Unit) {
        bookmarkViewModel.setBookmarkCountChangedCallback { delta ->
            sharedBookmarkCountViewModel.updateBookmarkCountImmediate(delta)
        }
    }
    
    val filteredNames = names.filter {
        searchQuery.isBlank() ||
        it.arabic.contains(searchQuery, ignoreCase = true) ||
        it.transliteration.contains(searchQuery, ignoreCase = true) ||
        it.english.contains(searchQuery, ignoreCase = true) ||
        it.meaning.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            RamadanToolbar(
                title = stringResource(R.string.allah_name), 
                showBack = true, 
                onBackClick = onBack,
                rightIcon1 = R.drawable.ic_saved,
                rightIcon1Badge = bookmarkCount,
                onRightIcon1Click = {
                    // Navigate to bookmarks list
                    navController.navigate(Routes.BOOKMARKS)
                }
            )
        }, containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Search field
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Search Allah Names...") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = { keyboardController?.hide() }
                )
            )

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredNames, key = { it.id }) { name ->
                    AllahNameCard(
                        name = name, onClick = { onNameClick(name) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AllahNamesScreenPreview() {
    MaterialTheme {
        // Create a mock ViewModel for preview
        val mockBookmarkCountViewModel = BookmarkCountViewModel(android.app.Application())
        
        AllahNamesScreen(
            names = listOf(
            AllahName(
                id = 1,
                arabic = "الرَّحْمٰنُ",
                transliteration = "Ar-Rahman",
                english = "The Most Merciful",
                meaning = "The One who has plenty of mercy for the believers."
            ), AllahName(
                id = 2,
                arabic = "الرَّحِيمُ",
                transliteration = "Ar-Raheem",
                english = "The Most Compassionate",
                meaning = "The One who bestows mercy upon the believers."
            ), AllahName(
                id = 3,
                arabic = "الْمَلِكُ",
                transliteration = "Al-Malik",
                english = "The King",
                meaning = "The Absolute Ruler of the universe."
            )
        ), onBack = {}, onNameClick = {}, navController = rememberNavController(), sharedBookmarkCountViewModel = mockBookmarkCountViewModel)
    }
}

@Preview(
    showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun AllahNamesScreenDarkPreview() {
    MaterialTheme {
        // Create a mock ViewModel for preview
        val mockBookmarkCountViewModel = BookmarkCountViewModel(android.app.Application())
        
        AllahNamesScreen(
            names = listOf(
            AllahName(
                id = 55,
                arabic = "الرَّحْمٰنُ",
                transliteration = "Ar-Rahman",
                english = "The Most Merciful",
                meaning = "The One who has plenty of mercy for the believers."
            )
        ), onBack = {}, onNameClick = {}, navController = rememberNavController(), sharedBookmarkCountViewModel = mockBookmarkCountViewModel)
    }
}
