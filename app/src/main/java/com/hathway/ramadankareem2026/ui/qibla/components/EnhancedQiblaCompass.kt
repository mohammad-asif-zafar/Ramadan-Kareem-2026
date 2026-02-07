package com.hathway.ramadankareem2026.ui.qibla.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.theme.RamadanGreen

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
            contentDescription = stringResource(R.string.current_direction),
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
            contentDescription = stringResource(R.string.qibla_direction),
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
            text = "W",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = RamadanGreen,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 8.dp)
        )

        // East
        Text(
            text = "S",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .offset(x = (-8).dp)
        )

        // South
        Text(
            text = "E",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-8).dp)
        )

        // West
        Text(
            text = "N",
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


