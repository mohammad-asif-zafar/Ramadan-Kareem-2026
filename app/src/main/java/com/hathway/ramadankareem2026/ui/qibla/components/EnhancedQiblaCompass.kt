package com.hathway.ramadankareem2026.ui.qibla.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.theme.RamadanGreen
import com.hathway.ramadankareem2026.ui.theme.RamadanGold
import kotlin.math.*

@Composable
fun EnhancedQiblaCompassCircle(
    deviceRotation: Float,
    qiblaRotation: Float,
    isAligned: Boolean,
    accuracy: Int
) {
    val animatedAlignment by animateFloatAsState(
        targetValue = if (isAligned) 1f else 0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    val pulseAnimation by rememberInfiniteTransition().animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .size(340.dp)
            .shadow(
                elevation = 20.dp,
                shape = CircleShape
            )
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.White,
                        MaterialTheme.colorScheme.surfaceVariant
                    )
                ),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        // Outer decorative ring
        if (isAligned) {
            Box(
                modifier = Modifier
                    .size(340.dp)
                    .graphicsLayer {
                        scaleX = pulseAnimation
                        scaleY = pulseAnimation
                        alpha = 0.3f * animatedAlignment
                    }
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                RamadanGreen.copy(alpha = 0.3f),
                                Color.Transparent
                            )
                        ),
                        shape = CircleShape
                    )
            )
        }

        // Compass background with enhanced design
        androidx.compose.foundation.Image(
            painter = painterResource(R.drawable.ic_compass_ring),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        // Direction labels
        CompassDirectionLabels()

        // Device direction needle (blue/teal) with enhanced design
        androidx.compose.foundation.Image(
            painter = painterResource(R.drawable.ic_device_needle),
            contentDescription = "Current Direction",
            modifier = Modifier
                .size(200.dp)
                .graphicsLayer {
                    rotationZ = deviceRotation
                    alpha = 0.8f
                }
        )

        // Qibla needle (green/gold) with enhanced design
        androidx.compose.foundation.Image(
            painter = painterResource(R.drawable.ic_qibla_needle),
            contentDescription = "Qibla Direction",
            modifier = Modifier
                .size(200.dp)
                .graphicsLayer {
                    rotationZ = qiblaRotation
                    alpha = 0.9f + 0.1f * animatedAlignment
                }
        )

        // Center cap with enhanced design
        EnhancedCenterCap(isAligned = isAligned)

        // Accuracy indicator
        AccuracyIndicator(accuracy = accuracy)
    }
}

@Composable
private fun CompassDirectionLabels() {
    Box(modifier = Modifier.size(320.dp)) {
        // North
        Text(
            text = "N",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = RamadanGreen,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 8.dp)
        )

        // East
        Text(
            text = "E",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .offset(x = (-8).dp)
        )

        // South
        Text(
            text = "S",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-8).dp)
        )

        // West
        Text(
            text = "W",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset(x = 8.dp)
        )
    }
}

@Composable
private fun EnhancedCenterCap(isAligned: Boolean) {
    val capColor by animateColorAsState(
        targetValue = if (isAligned) RamadanGreen else MaterialTheme.colorScheme.primary,
        animationSpec = tween(300)
    )

    Box(
        modifier = Modifier
            .size(20.dp)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        capColor,
                        capColor.copy(alpha = 0.7f)
                    )
                ),
                shape = CircleShape
            )
            .shadow(
                elevation = 4.dp,
                shape = CircleShape
            )
    )
}

@Composable
private fun AccuracyIndicator(accuracy: Int) {
    val accuracyColor = when (accuracy) {
        3 -> Color.Green // High accuracy
        2 -> Color(0xFFFFA500) // Medium accuracy (Orange)
        1 -> Color.Red   // Low accuracy
        else -> Color.Gray
    }

    val accuracyText = when (accuracy) {
        3 -> "High"
        2 -> "Medium"
        1 -> "Low"
        else -> "Unknown"
    }

    Box(
        modifier = Modifier
            .size(8.dp)
            .background(accuracyColor, CircleShape)
    )

    Spacer(modifier = Modifier.height(4.dp))

    Text(
        text = accuracyText,
        style = MaterialTheme.typography.labelSmall,
        color = accuracyColor,
        fontSize = 10.sp
    )
}

@Composable
fun QiblaCompassDecorations() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(horizontal = 16.dp)
    ) {
        // Left decoration - Islamic pattern
        Canvas(
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.CenterStart)
        ) {
            drawIslamicPattern(center = Offset(40f, 40f), radius = 35f)
        }

        // Right decoration - Islamic pattern
        Canvas(
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.CenterEnd)
        ) {
            drawIslamicPattern(center = Offset(40f, 40f), radius = 35f)
        }
    }
}

private fun DrawScope.drawIslamicPattern(center: Offset, radius: Float) {
    // Draw octagonal Islamic pattern
    val sides = 8
    val angleStep = (2 * PI) / sides

    for (i in 0 until sides) {
        val angle = i * angleStep
        val nextAngle = (i + 1) * angleStep
        
        val x1 = center.x + radius * cos(angle).toFloat()
        val y1 = center.y + radius * sin(angle).toFloat()
        val x2 = center.x + radius * cos(nextAngle).toFloat()
        val y2 = center.y + radius * sin(nextAngle).toFloat()
        
        drawLine(
            color = RamadanGreen.copy(alpha = 0.3f),
            start = Offset(x1, y1),
            end = Offset(x2, y2),
            strokeWidth = 2.dp.toPx(),
            cap = StrokeCap.Round
        )
    }

    // Inner circle
    drawCircle(
        color = RamadanGold.copy(alpha = 0.5f),
        radius = radius * 0.3f,
        center = center,
        style = Stroke(width = 1.dp.toPx())
    )
}
