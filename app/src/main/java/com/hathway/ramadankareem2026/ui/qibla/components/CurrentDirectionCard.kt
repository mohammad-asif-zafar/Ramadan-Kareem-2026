package com.hathway.ramadankareem2026.ui.qibla.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.ui.theme.RamadanGreen
import com.hathway.ramadankareem2026.ui.theme.RamadanGold
import kotlin.math.*

@Composable
fun CurrentDirectionCard(
    currentDirection: Float,
    isAligned: Boolean
) {
    val animatedDirection by animateFloatAsState(
        targetValue = currentDirection,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    val directionName = getDirectionName(animatedDirection)
    val directionIcon = getDirectionIcon(animatedDirection)
    
    val cardColor by animateColorAsState(
        targetValue = if (isAligned) {
            RamadanGreen.copy(alpha = 0.1f)
        } else {
            MaterialTheme.colorScheme.surface
        },
        animationSpec = tween(300)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = "Current Direction",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Direction Display
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                // Direction Icon
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    RamadanGold,
                                    RamadanGold.copy(alpha = 0.7f)
                                )
                            ),
                            shape = RoundedCornerShape(24.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = directionIcon,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Direction Info
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = directionName,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = RamadanGreen
                    )

                    Text(
                        text = "${String.format("%.1f", animatedDirection)}°",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Compass Rose Mini
            MiniCompassRose(currentDirection = animatedDirection)

            Spacer(modifier = Modifier.height(16.dp))

            // Status Message
            StatusMessage(
                currentDirection = animatedDirection,
                isAligned = isAligned
            )
        }
    }
}

@Composable
private fun MiniCompassRose(currentDirection: Float) {
    Box(
        modifier = Modifier.size(80.dp),
        contentAlignment = Alignment.Center
    ) {
        // Background circle
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(40.dp)
                )
        )

        // Direction indicators
        listOf("N", "E", "S", "W").forEachIndexed { index, direction ->
            val angle = index * 90f
            val isCurrent = abs((currentDirection - angle).let { 
                if (it > 180) 360 - it else it 
            }) < 22.5f

            Text(
                text = direction,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                color = if (isCurrent) RamadanGreen else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .offset(
                        x = (30 * sin(Math.toRadians(angle.toDouble()))).dp,
                        y = (-30 * cos(Math.toRadians(angle.toDouble()))).dp
                    )
            )
        }

        // Center dot
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(
                    RamadanGold,
                    shape = RoundedCornerShape(4.dp)
                )
        )
    }
}

@Composable
private fun StatusMessage(
    currentDirection: Float,
    isAligned: Boolean
) {
    val qiblaDirection = 81f // This should come from the actual calculation
    val difference = abs((currentDirection - qiblaDirection).let { 
        if (it > 180) 360 - it else it 
    })

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = if (isAligned) Icons.Default.CheckCircle else Icons.Default.Info,
            contentDescription = null,
            tint = if (isAligned) RamadanGreen else MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = when {
                isAligned -> "Perfectly aligned with Qibla!"
                difference < 10f -> "Very close to Qibla (${String.format("%.1f", difference)}° away)"
                difference < 30f -> "Near Qibla direction (${String.format("%.1f", difference)}° away)"
                else -> "Turn ${if (currentDirection < qiblaDirection) "right" else "left"} to face Qibla"
            },
            style = MaterialTheme.typography.bodyMedium,
            color = if (isAligned) RamadanGreen else MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

private fun getDirectionName(direction: Float): String {
    val normalizedDirection = (direction + 360) % 360
    return when {
        normalizedDirection < 22.5f || normalizedDirection >= 337.5f -> "North"
        normalizedDirection < 67.5f -> "Northeast"
        normalizedDirection < 112.5f -> "East"
        normalizedDirection < 157.5f -> "Southeast"
        normalizedDirection < 202.5f -> "South"
        normalizedDirection < 247.5f -> "Southwest"
        normalizedDirection < 292.5f -> "West"
        else -> "Northwest"
    }
}

private fun getDirectionIcon(direction: Float): ImageVector {
    val normalizedDirection = (direction + 360) % 360
    return when {
        normalizedDirection < 22.5f || normalizedDirection >= 337.5f -> Icons.Default.North
        normalizedDirection < 67.5f -> Icons.Default.NorthEast
        normalizedDirection < 112.5f -> Icons.Default.East
        normalizedDirection < 157.5f -> Icons.Default.SouthEast
        normalizedDirection < 202.5f -> Icons.Default.South
        normalizedDirection < 247.5f -> Icons.Default.SouthWest
        normalizedDirection < 292.5f -> Icons.Default.West
        else -> Icons.Default.NorthWest
    }
}
