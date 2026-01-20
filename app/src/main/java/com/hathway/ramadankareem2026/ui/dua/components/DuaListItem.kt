package com.hathway.ramadankareem2026.ui.dua.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.ramadankareem2026.ui.theme.RamadanKareemTheme

/**
 * 📖 A single Duʿā card used in lists (category screen, all duʿās, etc.)
 *
 * Shows:
 *  - Left icon (book / Qur’an)
 *  - Title (duʿā name)
 *  - Optional subtitle (source like Qur’an 2:128)
 *  - Right chevron (indicates navigation)
 */
@Composable
fun DuaCardItem(
    title: String,                 // 🏷️ Duʿā title
    subtitle: String? = null,       // 📌 Optional source
    onClick: () -> Unit             // 👉 Navigate to detail
) {

    // 🧱 Card container
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick), // Whole card clickable
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {

        // 🔹 Horizontal layout
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // 📘 Left icon (visual anchor)
            Icon(
                imageVector = Icons.Outlined.AutoStories,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // 🕌 Main text content
            Column(
                modifier = Modifier.weight(1f) // Takes remaining space
            ) {

                // 🔠 Title
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                // 📌 Subtitle (optional)
                if (!subtitle.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // ➡️ Right chevron (navigation hint)
            Icon(
                imageVector = Icons.Outlined.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


@Preview(
    name = "Dua Card Item", device = Devices.PIXEL_6, showBackground = true
)
@Composable
fun DuaCardItemPreview() {
    RamadanKareemTheme {
        Column(
            modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // With subtitle
            DuaCardItem(
                title = "Ramadan Moon Sighting Duʿā", subtitle = "Ramadan Duʿā", onClick = {})

            // Without subtitle
            DuaCardItem(
                title = "Forgiveness Duʿā", subtitle = null, onClick = {})
        }
    }
}
