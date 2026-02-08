package com.hathway.ramadankareem2026.ui.tips.presentation.components

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.hathway.ramadankareem2026.ui.theme.RamadanGold
import com.hathway.ramadankareem2026.ui.theme.RamadanGreen
import com.hathway.ramadankareem2026.ui.tips.data.model.Tip

@Composable
fun DailyTipCard(
    tip: Tip,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    language: String = "en"
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header with icon and title
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Gold accent icon
                    Surface(
                        modifier = Modifier.size(28.dp),
                        shape = RoundedCornerShape(8.dp),
                        color = RamadanGold.copy(alpha = 0.15f)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "✨",
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.width(10.dp))
                    
                    Text(
                        text = "Today's Tip",
                        style = MaterialTheme.typography.labelMedium,
                        color = RamadanGold,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                if (tip.isDaily) {
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp)),
                        color = RamadanGreen.copy(alpha = 0.1f)
                    ) {
                        Text(
                            text = "Daily",
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = RamadanGreen,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(14.dp))
            
            // Title
            Text(
                text = tip.title.getText(language),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(10.dp))
            
            // Content
            Text(
                text = tip.content.getText(language),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 1.5.em
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Bottom section with divider and CTA
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
            )
            
            Spacer(modifier = Modifier.height(10.dp))
            
            // Tap to read more indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Read more",
                    style = MaterialTheme.typography.labelSmall,
                    color = RamadanGold,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "→",
                    style = MaterialTheme.typography.labelSmall,
                    color = RamadanGold
                )
            }
        }
    }
}
