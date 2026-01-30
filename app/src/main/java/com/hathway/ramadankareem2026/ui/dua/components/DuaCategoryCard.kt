package com.hathway.ramadankareem2026.ui.dua.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.ui.dua.model.DuaCategory
import com.hathway.ramadankareem2026.ui.theme.RamadanKareemTheme

/**
 *  Dua Category Card
 *
 * Used in:
 *  - Dua main screen (2×2 grid)
 *  - Category selection screens
 *
 * Shows:
 *  - Category icon
 *  - Category title
 *  - Short subtitle/description
 */
@Composable
fun DuaCategoryCard(
    modifier: Modifier = Modifier, category: DuaCategory, onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(140.dp)         // ✅ Fixed height for grid consistency
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(3.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {

        //  Vertical content layout
        Column(
            modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            // 🧿 Category icon
            Icon(
                imageVector = category.icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )

            //  Category title
            Text(
                text = category.title, style = MaterialTheme.typography.titleMedium
            )

            // 📝 Category subtitle
            Text(
                text = category.subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


private val previewCategory = DuaCategory(
    id = "quran",
    title = "Duʿāʾs from Qur’an",
    subtitle = "Supplications revealed in the Qur’an",
    icon = Icons.Outlined.AutoStories
)

@Preview(
    name = "Dua Category Card", device = Devices.PIXEL_6, showBackground = true
)
@Composable
fun DuaCategoryCardPreview() {
    RamadanKareemTheme {
        Column(
            modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DuaCategoryCard(
                category = previewCategory, onClick = {})
        }
    }
}
