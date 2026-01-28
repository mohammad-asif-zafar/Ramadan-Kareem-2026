package com.hathway.ramadankareem2026.ui.quran.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.ui.quran.domain.model.Ayah

@Composable
fun AyahItem(
    surahId: Int,
    ayah: Ayah,
    isBookmarked: Boolean,
    onBookmarkClick: () -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            // ✅ Ayah click (row level)
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column(modifier = Modifier.weight(1f)) {

            Text(
                text = ayah.arabicText,
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = ayah.translation,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        // ⭐ Bookmark click (isolated, does NOT trigger row click)
        IconButton(onClick = onBookmarkClick) {
            Icon(
                imageVector = if (isBookmarked)
                    Icons.Default.Bookmark
                else Icons.Default.BookmarkBorder,
                contentDescription = "Bookmark ayah"
            )
        }
    }
}
