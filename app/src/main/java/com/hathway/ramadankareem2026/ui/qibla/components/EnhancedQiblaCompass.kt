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
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.theme.Emerald
import com.hathway.ramadankareem2026.ui.theme.Gold

@Composable
fun EnhancedQiblaCompassCircle(
    deviceRotation: Float,
    qiblaRotation: Float,
    isAligned: Boolean,
    accuracy: Int
) {
    val animatedAlignment by animateFloatAsState(
        targetValue = if (isAligned) 1f else 0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "alignment"
    )

    val pulseAnimation by rememberInfiniteTransition(label = "pulse").animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    val isDark = androidx.compose.foundation.isSystemInDarkTheme()

    Box(
        modifier = Modifier
            .size(320.dp)
            .shadow(
                elevation = if (isAligned) 12.dp else 4.dp,
                shape = CircleShape,
                ambientColor = if (isAligned) Emerald.copy(alpha = 0.4f) else Color.Black.copy(alpha = 0.1f),
                spotColor = if (isAligned) Emerald.copy(alpha = 0.4f) else Color.Black.copy(alpha = 0.1f)
            )
            .background(
                brush = Brush.radialGradient(
                    colors = if (isDark) {
                        listOf(Color(0xFF1B252E), Color(0xFF0F1720))
                    } else {
                        listOf(Color.White, Color(0xFFF1F4F2))
                    }
                ),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        // Outer decorative pulse ring when aligned
        if (isAligned) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        scaleX = pulseAnimation
                        scaleY = pulseAnimation
                    }
                    .border(
                        width = 2.dp,
                        brush = Brush.radialGradient(
                            colors = listOf(Emerald.copy(alpha = 0.5f), Color.Transparent)
                        ),
                        shape = CircleShape
                    )
            )
        }

        // Compass background ring
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .border(
                    width = 1.dp,
                    color = (if (isDark) Color.White else Color.Black).copy(alpha = 0.05f),
                    shape = CircleShape
                )
        )

        // Direction labels (N, E, S, W)
        CompassDirectionLabels(deviceRotation)

        // Qibla needle (Main focus)
        Box(
            modifier = Modifier
                .size(240.dp)
                .graphicsLayer {
                    rotationZ = qiblaRotation
                },
            contentAlignment = Alignment.Center
        ) {
            // Arrow pointing to Qibla
            androidx.compose.foundation.Image(
                painter = painterResource(R.drawable.ic_qibla_needle),
                contentDescription = stringResource(R.string.qibla_direction),
                modifier = Modifier.fillMaxSize(),
                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(
                    if (isAligned) Emerald else Gold
                )
            )
        }

        // Device direction needle (Subtle reference)
        Box(
            modifier = Modifier
                .size(180.dp)
                .graphicsLayer {
                    rotationZ = 0f // It's fixed relative to device
                    alpha = 0.3f
                },
            contentAlignment = Alignment.Center
        ) {
             androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
                 drawLine(
                     color = if (isDark) Color.White else Color.Black,
                     start = Offset(size.width / 2f, 0f),
                     end = Offset(size.width / 2f, size.height * 0.2f),
                     strokeWidth = 4.dp.toPx(),
                     cap = androidx.compose.ui.graphics.StrokeCap.Round
                 )
             }
        }

        // Center cap
        EnhancedCenterCap(isAligned = isAligned)
        
        // Bottom accuracy indicator
        Box(modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 40.dp)) {
            AccuracyIndicator(accuracy = accuracy)
        }
    }
}

@Composable
private fun CompassDirectionLabels(rotation: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .graphicsLayer { rotationZ = rotation }
    ) {
        val labelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        val activeColor = Emerald

        // North
        Text(
            text = "N",
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.ExtraBold),
            color = activeColor,
            modifier = Modifier.align(Alignment.TopCenter)
        )

        // East
        Text(
            text = "E",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
            color = labelColor,
            modifier = Modifier.align(Alignment.CenterEnd)
        )

        // South
        Text(
            text = "S",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
            color = labelColor,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        // West
        Text(
            text = "W",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
            color = labelColor,
            modifier = Modifier.align(Alignment.CenterStart)
        )
    }
}

@Composable
private fun EnhancedCenterCap(isAligned: Boolean) {
    val capColor by animateColorAsState(
        targetValue = if (isAligned) Emerald else Gold,
        animationSpec = tween(300),
        label = "capColor"
    )

    Box(
        modifier = Modifier
            .size(24.dp)
            .shadow(elevation = 4.dp, shape = CircleShape)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(Color.White, capColor)
                ),
                shape = CircleShape
            )
            .border(2.dp, capColor.copy(alpha = 0.5f), CircleShape)
    )
}

@Composable
private fun AccuracyIndicator(accuracy: Int) {
    val accuracyColor = when (accuracy) {
        3 -> Emerald // High
        2 -> Gold    // Medium
        else -> MaterialTheme.colorScheme.error // Low/Unreliable
    }

    val accuracyText = when (accuracy) {
        3 -> "High Accuracy"
        2 -> "Low Accuracy"
        else -> "Unreliable"
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(accuracyColor.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .background(accuracyColor, CircleShape)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = accuracyText,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            ),
            color = accuracyColor
        )
    }
}


