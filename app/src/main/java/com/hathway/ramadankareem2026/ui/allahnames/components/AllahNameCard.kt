package com.hathway.ramadankareem2026.ui.allahnames.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.ui.allahnames.domain.model.AllahName
import com.hathway.ramadankareem2026.ui.theme.RamadanGold
import com.hathway.ramadankareem2026.ui.theme.RamadanGreen

@Composable
fun AllahNameCard(
    name: AllahName, onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
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

            // Optional number badge
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        color = RamadanGreen.copy(alpha = 0.12f), shape = CircleShape
                    ), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = name.id.toString(),
                    style = MaterialTheme.typography.labelMedium,
                    color = RamadanGreen
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                // Arabic Name (GOLD)
                Text(
                    text = name.arabic,
                    style = MaterialTheme.typography.headlineSmall,
                    color = RamadanGold
                )

                Spacer(modifier = Modifier.height(4.dp))

                // English meaning
                Text(
                    text = name.meaning,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AllahNameCardPreview() {
    MaterialTheme {
        AllahNameCard(
            name = AllahName(
                id = 1,
                arabic = "الرَّحْمٰنُ",
                transliteration = "Ar-Rahman",
                english = "The Most Merciful",
                meaning = "The One who has plenty of mercy for the believers."
            ),
            onClick = {}
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun AllahNameCardDarkPreview() {
    MaterialTheme {
        AllahNameCard(
            name = AllahName(
                id = 55,
                arabic = "الرَّحْمٰنُ",
                transliteration = "Ar-Rahman",
                english = "The Most Merciful",
                meaning = "The One who has plenty of mercy for the believers."
            ),
            onClick = {}
        )
    }
}

