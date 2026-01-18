package com.hathway.ramadankareem2026.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.NightsStay
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.ui.home.model.HeaderType
import com.hathway.ramadankareem2026.ui.theme.RamadanGold
import com.hathway.ramadankareem2026.ui.theme.RamadanGreen
import com.hathway.ramadankareem2026.ui.theme.RamadanGreenDark

@Composable
fun HeaderCard(
    type: HeaderType, title: String, subtitle: String, hint: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .background(headerGradient(type))
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 20.dp,
                    bottom = 20.dp
                )
        ) {
            Column(
                modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween
            ) {

                // 🔝 Icon + Title
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = headerIcon(type),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )

                    Spacer(Modifier.width(8.dp))

                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // 🔹 Subtitle
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                // 🔻 Hint
                Text(
                    text = hint,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }
    }
}

private fun headerIcon(type: HeaderType) = when (type) {
    HeaderType.DYNAMIC_PRAYER -> Icons.Outlined.NightsStay
    HeaderType.NEXT_PRAYER -> Icons.Outlined.AccessTime
    HeaderType.REMINDER -> Icons.Outlined.AutoStories
}

@Composable
private fun headerGradient(type: HeaderType): Brush = when (type) {

    HeaderType.DYNAMIC_PRAYER -> Brush.linearGradient(
        colors = listOf(
            RamadanGreen, RamadanGreenDark
        )
    )

    HeaderType.NEXT_PRAYER -> Brush.linearGradient(
        colors = listOf(
            RamadanGreenDark, RamadanGreen
        )
    )

    HeaderType.REMINDER -> Brush.linearGradient(
        colors = listOf(
            RamadanGold, RamadanGreen
        )
    )
}



