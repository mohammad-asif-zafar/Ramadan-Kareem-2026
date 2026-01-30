package com.hathway.ramadankareem2026.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.ui.theme.RamadanGold

/**
 * Pager indicator (dots) used with HorizontalPager.
 *
 * Shows:
 * - One dot per page
 * - Highlighted dot for the current page
 *
 * Example:
 * ● ○ ○
 */
@Composable
fun PagerIndicator(
    pageCount: Int,                // Total number of pages
    currentPage: Int,              // Currently selected page index
    modifier: Modifier = Modifier  // Optional modifier from parent
) {

    /**
     * Row to place dots horizontally.
     * spacedBy → distance between dots
     */
    Row(
        modifier = modifier, horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {

        /**
         * Create one dot per page.
         */
        repeat(pageCount) { index ->

            /**
             * Each dot is a Box:
             * - Circle shape
             * - Slightly bigger for active page
             */
            Box(
                modifier = Modifier
                    .size(
                        if (index == currentPage) 8.dp else 6.dp
                    )
                    .clip(CircleShape)
                    .background(
                        color = if (index == currentPage) RamadanGold                     // Active dot
                        else Color.Gray.copy(alpha = 0.4f), // Inactive dot
                        shape = RoundedCornerShape(50)
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PagerIndicatorPreview() {

    Column(
        modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        // First page active
        PagerIndicator(
            pageCount = 3, currentPage = 0
        )

        // Middle page active
        PagerIndicator(
            pageCount = 3, currentPage = 1
        )

        // Last page active
        PagerIndicator(
            pageCount = 3, currentPage = 2
        )
    }
}

/*
 Animated indicator

 Pill-style indicator
 UI test example
 Theme-aware version
*/
