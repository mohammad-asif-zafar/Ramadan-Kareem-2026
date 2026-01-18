package com.hathway.ramadankareem2026.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.ui.theme.RamadanGold

@Composable
fun PagerIndicator(
    pageCount: Int, currentPage: Int, modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier, horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        repeat(pageCount) { index ->
            Box(
                modifier = Modifier
                    .size(if (index == currentPage) 8.dp else 6.dp)
                    .clip(CircleShape)
                    .background(
                        color = if (index == currentPage) RamadanGold
                        else Color.Companion.Gray.copy(alpha = 0.4f), shape = RoundedCornerShape(50)
                    )
            )
        }
    }
}
