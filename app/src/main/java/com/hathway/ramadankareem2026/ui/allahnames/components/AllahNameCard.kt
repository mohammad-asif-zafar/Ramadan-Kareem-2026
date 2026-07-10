package com.hathway.ramadankareem2026.ui.allahnames.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.ramadankareem2026.ui.allahnames.domain.model.AllahName
import com.hathway.ramadankareem2026.ui.theme.Emerald
import com.hathway.ramadankareem2026.ui.theme.Gold


/**
 * 🎴 OPTION 1: Grid Variation (2 by 2 Square Card Layout Pattern)
 */
@Composable
fun AllahNameCard(
    name: AllahName,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(0.85f), // Guarantees balanced square proportion framing matching your layout image
        shape = RoundedCornerShape(24.dp), // Enhanced deep corner roundness
        color = Color(0xFFF9F9F8), // Soft off-white tint background layer
        shadowElevation = 1.dp,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Centered Number Badge
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        color = Emerald.copy(alpha = 0.1f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = name.id.toString(),
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp),
                    color = Emerald,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Centered Golden Arabic Script
            Text(
                text = name.arabic,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = Gold,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Centered Meaning Details Label Block
            Text(
                text = name.meaning,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 16.sp
            )
        }
    }
}

/**
 * 📜 OPTION 2: List Variation (Full Width Horizontal Row Layout Pattern)
 */
@Composable
fun AllahNameRow(
    name: AllahName,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(20.dp), // Clean smooth edge mapping matching image snippet
        color = Color(0xFFF9F9F8),
        shadowElevation = 1.dp,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left Number Badge Container
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .background(
                        color = Emerald.copy(alpha = 0.1f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = name.id.toString(),
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 13.sp),
                    color = Emerald,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Details Stack Column
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = name.arabic,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Gold,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = name.meaning,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}



@Composable
fun NamesDisplaySection(
    isGridView: Boolean,
    filteredNames: List<AllahName>,
    onNameClick: (AllahName) -> Unit,
    modifier: Modifier = Modifier
) {
    // Crossfade switches layouts smoothly with a premium fade transition
    Crossfade(
        targetState = isGridView,
        modifier = modifier,
        label = "NamesLayoutSwitch"
    ) { gridActive ->
        if (gridActive) {
            // 🎴 Grid Layout Option: Draws items in a 2-by-2 square matrix configuration
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items = filteredNames, key = { it.id }) { name ->
                    AllahNameCard(
                        name = name,
                        onClick = { onNameClick(name) }
                    )
                }
            }
        } else {
            // 📜 List Layout Option: Renders stacked horizontal row cards
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items = filteredNames, key = { it.id }) { name ->
                    AllahNameRow(
                        name = name,
                        onClick = { onNameClick(name) }
                    )
                }
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

