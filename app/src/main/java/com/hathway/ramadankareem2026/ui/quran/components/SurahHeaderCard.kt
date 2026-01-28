package com.hathway.ramadankareem2026.ui.quran.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.ui.quran.domain.model.Surah

@Composable
fun SurahHeaderCard(surah: Surah) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(surah.name, style = MaterialTheme.typography.titleLarge)
            Text(
                "${surah.numberOfAyahs} verses • ${surah.revelationType}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
