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
 * A reusable card UI for showing a single Duʿā item in a list.
 *
 * Used in:
 * - Duʿā categories screen
 * - All Duʿās list
 * - Search results
 *
 * UI Structure:
 */
@Composable
fun DuaCardItem(
    title: String,           // Main duʿā title (required)
    subtitle: String? = null,// Optional subtitle (e.g., Qur’an reference)
    onClick: () -> Unit      // Click action (navigate to detail screen)
) {

    /**
     * Card acts as the main container.
     * - fillMaxWidth → full width list item
     * - clickable → entire card is tappable
     * - Rounded corners for modern UI
     */
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {

        /**
         * Row layout to arrange:
         * [Icon] [Text Content] [Chevron]
         */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Left icon (visual identifier for duʿā/book)
            Icon(
                imageVector = Icons.Outlined.AutoStories,
                contentDescription = null, // Decorative icon
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            /**
             * Column holds title and optional subtitle.
             * weight(1f) ensures it takes all available space
             * and pushes chevron to the right.
             */
            Column(
                modifier = Modifier.weight(1f)
            ) {

                // Duʿā title
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                /**
                 * Subtitle shown only if provided.
                 * Useful for:
                 * - Qur’an reference
                 * - Category name
                 * - Short description
                 */
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

            // Right chevron → indicates navigation
            Icon(
                imageVector = Icons.Outlined.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


/**
 * Preview to visualize the DuaCardItem in isolation.
 * Helps designers & developers verify spacing, typography, and layout.
 */
@Preview(
    name = "Dua Card Item", device = Devices.PIXEL_6, showBackground = true
)
@Composable
fun DuaCardItemPreview() {
    RamadanKareemTheme {

        /**
         * Column used to show multiple variations
         * with spacing between them.
         */
        Column(
            modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Example with subtitle
            DuaCardItem(
                title = "Ramadan Moon Sighting Duʿā", subtitle = "Ramadan Duʿā", onClick = {})

            // Example without subtitle
            DuaCardItem(
                title = "Forgiveness Duʿā", subtitle = null, onClick = {})
        }
    }
}
