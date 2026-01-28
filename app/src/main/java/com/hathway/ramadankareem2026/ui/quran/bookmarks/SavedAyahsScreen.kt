package com.hathway.ramadankareem2026.ui.quran.bookmarks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier


@Composable
fun SavedAyahsScreen(
    viewModel: SavedAyahsViewModel, onAyahClick: (surahId: Int, ayahNumber: Int) -> Unit
) {
    val saved by viewModel.savedAyahs.collectAsState(emptySet())

    LazyColumn {
        items(saved.toList()) { key ->
            val (surahId, ayahNumber) = key.split(":").map { it.toInt() }

            ListItem(
                headlineContent = { Text("Surah $surahId • Ayah $ayahNumber") },
                supportingContent = {
                    Text("Secondary text that is long and perhaps goes onto another line")
                },
                leadingContent = {
                    Icon(
                        Icons.Filled.Favorite,
                        contentDescription = "Localized description",
                    )
                },
                trailingContent = { Text("meta") },
                modifier = Modifier.clickable {
                    onAyahClick(surahId, ayahNumber)
                })
            HorizontalDivider()


        }
    }
}
